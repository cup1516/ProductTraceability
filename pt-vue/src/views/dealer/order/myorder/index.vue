<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dealer:node:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['dealer:node:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['dealer:node:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="nodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="节点编号" width="280" align="center" prop="nodeId" />
      <el-table-column label="节点编码" align="center" prop="nodeCode" />
      <el-table-column label="节点名称" align="center" prop="nodeName" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
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
            v-hasPermi="['dealer:node:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dealer:node:remove']"
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

    <!-- 添加或修改节点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" @closed="handleClose" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司名称" prop="companyName">
              <el-input  v-model="form.companyName"  size="mini" style="width:200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品类型" prop="productType">
              <el-input  v-model="form.productType"   size="mini" style="width:200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品批次" prop="productBatch">
              <el-input  v-model="form.productBatch"  size="mini" style="width:200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品数量" prop="productAmount">
              <el-input-number v-model="form.productAmount"  :min="0"  size="mini"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品单价" prop="productPrice">
              <el-input-number v-model="form.productPrice"  :min="0"  size="mini"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产品总价" prop="productTotal">
              <el-input :disabled="true" v-model="form.productTotal" size="mini" style="text-align:center" />
            </el-form-item> 
          </el-col>
          <el-col :span="24">
            <el-form-item label="审批人" prop="reviewerName">
              <el-input  v-model="form.reviewerName"  size="mini" style="width:200px"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- <el-form-item label="节点状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
              :disabled="isView"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item> -->

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNode ,getNode ,addNode ,updateNode,delNode} from "@/api/dealer/node";

export default {
  name: "node",
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
  computed: {
    // 计算属性的 getter
    productTotal: function () {
      this.form.productTotal = this.form.productPrice*this.form.productAmount
      // `this` 指向 vm 实例
      return this.form.productTotal
    }
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询节点列表 */
    getList() {
      this.loading = true;
      listNode(this.queryParams).then(response => {
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
        buyerId: '',
        buyerName: '',
        productId: '',
        productName: '',
        productAmount: 0,
        productPrice: 0,
        productTotal: 0,
      };
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
      this.ids = selection.map(item => item.nodeId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增订单";
    },
    /** 关闭对话框回调函数 */
    handleClose(){
      this.isView = false
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      this.isView = true
      const nodeId = row.nodeId || this.ids
      getNode(nodeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改节点";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const nodeId = row.nodeId || this.ids
      getNode(nodeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改节点";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.nodeId != undefined) {
            updateNode(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response);
            });
          } else {
            addNode(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
            }).catch(response=>{
              this.msgError(response);
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const nodeIds = row.nodeId || this.ids;
      this.$confirm('是否确认删除节点编号为"' + nodeIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          delNode(nodeIds).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(response=>{
              this.msgError(response);
            });
        })
    },
  }
};
</script>