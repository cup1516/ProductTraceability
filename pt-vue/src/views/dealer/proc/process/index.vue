<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="流程编码" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          placeholder="请输入流程编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="启用状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="启用状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="送审状态" prop="checkStatus">
        <el-select v-model="queryParams.checkStatus" placeholder="送审状态" clearable size="small">
          <el-option
            v-for="dict in checkStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人员" prop="createBy" v-hasPermi="['dealer:process:check']">
        <el-select v-model="queryParams.createBy" placeholder="创建人员" clearable size="small">
          <el-option
            v-for="item in userList"
            :key="item.userName"
            :label="item.nickName"
            :value="item.userName"
          ><span style="float: left;font-size: 6px" >{{ item.nickName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dealer:process:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dealer:process:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dealer:process:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="processList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程编号" width="280" align="center" prop="processId" />
      <el-table-column label="流程编码" align="center" prop="processCode" />
      <el-table-column label="流程名称" align="center" prop="processName" />
      <el-table-column label="启用状态" width="80" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="送审状态" width="80" align="center" prop="checkStatus" :formatter="checkStatusFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.checkStatus === '0'||scope.row.checkStatus === '3'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleCheck(scope.row)"
            v-hasPermi="['dealer:process:edit']"
          >送审</el-button>
          <el-button
            v-if="scope.row.checkStatus === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleRetrack(scope.row)"
            v-hasPermi="['dealer:process:edit']"
          >撤回</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handlePass(scope.row)"
            v-hasPermi="['dealer:process:check']"
          >通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleFail(scope.row)"
            v-hasPermi="['dealer:process:check']"
          >打回</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dealer:process:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dealer:process:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.current"
      :limit.sync="queryParams.size"
      @pagination="getList"
    />

    <!-- 添加或修改流程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" @closed="handleClose" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="流程名称" prop="processName">
              <el-input :disabled="isView" v-model="form.processName" placeholder="请输入流程名称" />
            </el-form-item>           
          </el-col>
          <el-col :span="12">
            <el-form-item label="流程编码" prop="processCode">
          <el-input :disabled="isView" v-model="form.processCode" placeholder="请输入编码名称" />
            </el-form-item>       
          </el-col>
        </el-row>
        <el-form-item label="流程节点" prop="processNodes">
          <el-button 
            v-if="!isView"
            icon="el-icon-plus"
            size="mini"
            @click="handleRowAdd"
            circle></el-button>
          <el-button 
              v-if="!isView"
              icon="el-icon-minus"
              size="mini"
              @click="handleRowDelete"
              :disabled="rowIds.length === 0"
              circle></el-button>
              <el-table
                    style="width: 100%"
                    :disabled="isView"
                    @selection-change="handleRowSelectionChange"
                    :data="form.processNodes">
                  <el-table-column v-if="!isView" type="selection" width="55" align="center" />
                  <el-table-column label="序号" type="index" :index="rowIndex" width="55" align="center" />
                  <el-table-column label="节点" align="center" prop="nodeId">
                    <template slot-scope="scope">
                      <el-select size="mini" placeholder=" " v-model="scope.row.nodeId" :disabled="isView">
                          <el-option
                            v-for="item in nodeList"
                            :key="item.nodeId"
                            :label="item.nodeName"
                            :value="item.nodeId"
                          >
                                <span style="float: left;font-size: 6px" >{{ item.nodeName }}</span>
                                <span style="float: right; color: #8492a6; font-size: 6px">({{ item.nodeCode }})</span>
                          </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作人员" align="center" prop="operator">
                    <template slot-scope="scope">
                      <el-select size="mini" placeholder=" " v-model="scope.row.workerId" :disabled="isView">
                          <el-option
                            v-for="item in workerList"
                            :key="item.userId"
                            :label="item.nickName"
                            :value="item.userId"
                          ><span style="float: left;font-size: 6px" >{{ item.nickName }}</span>
                          </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column label="开始时间" align="center" prop="startTime" width="180">
                     <template slot-scope="scope">
                        <el-date-picker
                          v-model="scope.row.startTime"
                          size="mini"
                          style="width: 165px"
                          value-format="yyyy-MM-dd HH-mm"
                          format="yyyy-MM-dd HH-mm"
                          type="datetime"
                          :disabled="isView"
                        ></el-date-picker>
                     </template>
                  </el-table-column>
                  <el-table-column label="结束时间" align="center" prop="endTime" width="180">
                     <template slot-scope="scope">
                        <el-date-picker
                          v-model="scope.row.endTime"
                          size="mini"
                          style="width: 165px"
                          value-format="yyyy-MM-dd HH-mm"
                          format="yyyy-MM-dd HH-mm"
                          type="datetime"
                          :disabled="isView"
                        ></el-date-picker>
                     </template>
                  </el-table-column>
                  <el-table-column label="状态" width="120px" align="center" prop="status">
                    <template slot-scope="scope">
                      <el-select size="mini" placeholder=" " v-model="scope.row.status" :disabled="isView">
                          <el-option
                            v-for="dict in nodeStatusOptions"
                          :key="dict.dictValue"
                          :label="dict.dictLabel"
                          :value="dict.dictValue"
                          >
                          </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
              </el-table>               
        </el-form-item>
        <el-form-item label="流程状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
              :disabled="isView"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"  :disabled="isView" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProcess,addProcess,updateProcess,delProcess,getProcess,changeCheckStatus} from "@/api/dealer/process";
import { getUserListPost, getUserListDept } from "@/api/system/user";
import { getList as getNodeList } from "@/api/dealer/node";
export default {
  name: "process",
  data() {
    return {
      // 是否查看
      isView: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中节点数组
      rowIds: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 流程表格数据
      processList: [],
      // 节点数据
      nodeList: [],
      // 工人数据
      workerList: [],
      // 部门人员数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 审批状态数据字典
      checkStatusOptions: [],
      // 节点完成状态
      nodeStatusOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        processCode: undefined,
        processName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processName: [
          { required: true, message: "流程名称不能为空", trigger: "blur" }
        ],
        processCode: [
          { required: true, message: "流程编码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("dealer_process_status").then(response => {
      this.nodeStatusOptions = response.data;
    });
    this.getDicts("dealer_check_status").then(response => {
      this.checkStatusOptions = response.data;
    });
    getUserListPost("WORKER").then(response => {
      this.workerList = response.data;
    })
    getUserListDept().then(response =>{
      this.userList = response.data;
    })
  },
  methods: {
    /** 查询流程列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 流程状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 流程完成状态字典翻译
    checkStatusFormat(row, column) {
      return this.selectDictLabel(this.checkStatusOptions, row.checkStatus);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        processId: undefined,
        processCode: undefined,
        processName: undefined,
        processNodes:[],
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.processId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getNodeList().then(res=>{
        this.nodeList = res.data
      })
      this.open = true;
      this.title = "添加流程";
    },
    /** 关闭对话框回调函数 */
    handleClose(){
      this.isView = false
    },
    /** 通过按钮操作 */
    handlePass(row){
        row.checkStatus = '2'
        changeCheckStatus(row).then(()=>{
          this.msgSuccess("通过成功")
          this.getList
        }).catch(res =>{
          this.msgError(res)
        })
    },
    /** 打回按钮操作 */
    handleFail(row){
        row.checkStatus = '3'
        changeCheckStatus(row).then(()=>{
          this.msgSuccess("打回成功")
          this.getList
        }).catch(res =>{
          this.msgError(res)
        })
    },
    /** 送审按钮操作 */
    handleCheck(row){
        row.checkStatus = '1'
        changeCheckStatus(row).then(()=>{
          this.msgSuccess("送审成功")
          this.getList
        }).catch(res =>{
          this.msgError(res)
        })
    },
    /** 撤回按钮操作 */
    handleRetrack(row){
        row.checkStatus = '0'
        changeCheckStatus(row).then(()=>{
          this.msgSuccess("撤回成功")
          this.getList
        }).catch(res =>{
          this.msgError(res)
        })
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      this.isView = true
      const processId = row.processId || this.ids
      getNodeList().then(res=>{
        this.nodeList = res.data
      })
      getProcess(processId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看流程";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const processId = row.processId || this.ids
      getNodeList().then(res=>{
        this.nodeList = res.data
      })
      getProcess(processId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.processId != undefined) {
            updateProcess(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response.msg);
            });
          } else {
            addProcess(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response.msg);
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const processIds = row.processId || this.ids;
      this.$confirm('是否确认删除流程编号为"' + processIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProcess(processIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },


    //添加行
    handleRowAdd(){
      let row = {
        nodeId: '',
        sort: '',
        remark: '',
        status: '0',
        
      }
      this.form.processNodes.push(row)
    },
    //在行数据中添加index
    rowIndex(index){
      this.form.processNodes[index].index = index
      this.form.processNodes[index].sort = index+1
      return index+1
    },
    // 对话框多选框选中数据
    handleRowSelectionChange(selection) {
      this.rowIds = selection.map(item => item.index)
    },
    //删除行
    handleRowDelete(){
      this.rowIds.forEach((id,index) => this.form.processNodes.splice(id-index,1))
      this.rowIds = []
    },
  }
};
</script>