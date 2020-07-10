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
      <el-form-item label="节点编码" prop="nodeCode">
        <el-input
          v-model="queryParams.nodeCode"
          placeholder="请输入节点编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="节点名称" prop="nodeName">
        <el-input
          v-model="queryParams.nodeName"
          placeholder="请输入节点名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="节点状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
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


    <el-table v-loading="loading" :data="nodeList" >
      <el-table-column type="index" width="55" align="center" />
      <el-table-column label="流程编码" align="center" prop="processCode" />
      <el-table-column label="流程名称" align="center" prop="processName" />
      <el-table-column label="节点编码" align="center" prop="nodeCode" />
      <el-table-column label="节点名称" align="center" prop="nodeName" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status == '0'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="changeStatus(scope.row)"
          >完成</el-button>
          <el-button
            v-if="scope.row.status == '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="changeStatus(scope.row)"
          >取消</el-button>
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


  </div>
</template>

<script>
import { listMission,changeStatus } from "@/api/dealer/mission";

export default {
  name: "mission",
  data() {
    return {
      // 是否查看
      isView: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 节点表格数据
      nodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        nodeCode: undefined,
        nodeName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nodeName: [
          { required: true, message: "节点名称不能为空", trigger: "blur" }
        ],
        nodeCode: [
          { required: true, message: "节点编码不能为空", trigger: "blur" }
        ],
        nodeSort: [
          { required: true, message: "节点顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("dealer_process_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询节点列表 */
    getList() {
      this.loading = true;
      listMission(this.queryParams).then(response => {
        this.nodeList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 节点状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        nodeId: undefined,
        nodeCode: undefined,
        nodeName: undefined,
        nodeSort: 0,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    changeStatus(row){
      status = row.status == '0'? '1':'0'
      changeStatus(row,status).then(()=>{
        this.getList()
        this.msgSuccess("操作成功");
      }).catch(res =>{
        this.msgError(res)
      })
    }
  }
};
</script>