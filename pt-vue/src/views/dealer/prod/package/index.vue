<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="包装编码" prop="packageCode">
        <el-input
          v-model="queryParams.packageCode"
          placeholder="请输入包装编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="包装名称" prop="packageName">
        <el-input
          v-model="queryParams.packageName"
          placeholder="请输入包装名称"
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
          v-hasPermi="['dealer:package:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dealer:package:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dealer:package:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="packageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="包装编号" width="280" align="center" prop="packageId" />
      <el-table-column label="包装编码" align="center" prop="packageCode" />
      <el-table-column label="包装名称" align="center" prop="packageName" />
      <el-table-column label="启用状态" width="80" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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
            v-hasPermi="['dealer:package:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dealer:package:remove']"
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

    <!-- 添加或修改包装对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" @closed="handleClose" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="包装名称" prop="packageName">
              <el-input :disabled="isView" v-model="form.packageName" placeholder="请输入包装名称" />
            </el-form-item>           
          </el-col>
          <el-col :span="12">
            <el-form-item label="包装编码" prop="packageCode">
          <el-input :disabled="isView" v-model="form.packageCode" placeholder="请输入编码名称" />
            </el-form-item>       
          </el-col>
        </el-row>
        <el-form-item label="包装单位" prop="packageUnits">
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
                    :data="form.packageUnits">
                  <el-table-column v-if="!isView" type="selection" width="55" align="center" />
                  <el-table-column label="级别" type="index" :index="rowIndex" width="55" align="center" />
                  <el-table-column label="单位" align="center" prop="unitId">
                    <template slot-scope="scope">
                      <el-select size="mini" placeholder=" " v-model="scope.row.unitId" :disabled="isView">
                          <el-option
                            v-for="item in unitList"
                            :key="item.unitId"
                            :label="item.unitName"
                            :value="item.unitId"
                          >
                                <span style="float: left;font-size: 6px" >{{ item.unitName }}</span>
                                <span style="float: right; color: #8492a6; font-size: 6px">({{ item.unitCode }})</span>
                          </el-option>
                      </el-select>
                    </template>
                  </el-table-column>
                  <el-table-column label="数量" align="center" prop="number" >
                    <template slot-scope="scope">
                      <el-input v-model="scope.row.number" size="mini" :disabled="isView"></el-input>
                    </template>
                  </el-table-column>
              </el-table>               
        </el-form-item>
        <el-form-item label="包装状态" prop="status">
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
import { listPackage,addPackage,updatePackage,delPackage,getPackage} from "@/api/dealer/package";
import { getList as getUnitList } from "@/api/dealer/unit";
export default {
  name: "package",
  data() {
    return {
      // 是否查看
      isView: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中单位数组
      rowIds: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 包装表格数据
      packageList: [],
      // 单位数据
      unitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 审批状态数据字典
      checkStatusOptions: [],
      // 单位完成状态
      unitStatusOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        packageCode: undefined,
        packageName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        packageName: [
          { required: true, message: "包装名称不能为空", trigger: "blur" }
        ],
        packageCode: [
          { required: true, message: "包装编码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("dealer_package_status").then(response => {
      this.unitStatusOptions = response.data;
    });
    this.getDicts("dealer_check_status").then(response => {
      this.checkStatusOptions = response.data;
    });
  },
  methods: {
    /** 查询包装列表 */
    getList() {
      this.loading = true;
      listPackage(this.queryParams).then(response => {
        this.packageList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 包装状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 包装完成状态字典翻译
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
        packageId: undefined,
        packageCode: undefined,
        packageName: undefined,
        packageUnits:[],
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
      this.ids = selection.map(item => item.packageId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getUnitList().then(res=>{
        this.unitList = res.data
      })
      this.open = true;
      this.title = "添加包装";
    },
    /** 关闭对话框回调函数 */
    handleClose(){
      this.isView = false
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      this.isView = true
      const packageId = row.packageId || this.ids
      getUnitList().then(res=>{
        this.unitList = res.data
      })
      getPackage(packageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看包装";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const packageId = row.packageId || this.ids
      getUnitList().then(res=>{
        this.unitList = res.data
      })
      getPackage(packageId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改包装";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.packageId != undefined) {
            updatePackage(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response.msg);
            });
          } else {
            addPackage(this.form).then(response => {
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
      const packageIds = row.packageId || this.ids;
      this.$confirm('是否确认删除包装编号为"' + packageIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delPackage(packageIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },


    //添加行
    handleRowAdd(){
      let row = {
        unitId: '',
        sort: '',
        number:'',  
      }
      this.form.packageUnits.push(row)
    },
    //在行数据中添加index
    rowIndex(index){
      this.form.packageUnits[index].index = index
      this.form.packageUnits[index].sort = index+1
      return index+1
    },
    // 对话框多选框选中数据
    handleRowSelectionChange(selection) {
      this.rowIds = selection.map(item => item.index)
    },
    //删除行
    handleRowDelete(){
      this.rowIds.forEach((id,index) => this.form.packageUnits.splice(id-index,1))
      this.rowIds = []
    },
  }
};
</script>