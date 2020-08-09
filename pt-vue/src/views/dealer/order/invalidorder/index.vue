<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder=" "
          clearable
          size="mini"
          style="width:140px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="买方公司" prop="buyerId">
        <el-select
          v-model="queryParams.buyerId"
          size="mini"
          style="width:140px"
          filterable
          clearable 
          remote
          reserve-keyword
          placeholder=" "
          :remote-method="listCompany"
          :loading="loading">
            <el-option
              v-for="item in companyOptions"
              :key="item.companyId"
              :label="item.companyName"
              :value="item.companyId">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产品批次" prop="productBatch">
        <el-input
          v-model="queryParams.productBatch"
          placeholder=" "
          clearable
          size="mini"
          style="width:140px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品名称" prop="productId">
        <el-select
          v-model="queryParams.productId"
          size="mini"
          style="width:140px"
          filterable
          remote
          clearable 
          reserve-keyword
          placeholder=" "
          :remote-method="listProduct"
          :loading="loading">
              <el-option
                v-for="item in typeOptions"
                :key="item.typeId"
                :label="item.typeName"
                :value="item.typeId">
              </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="creatorId">
        <el-select
            v-model="queryParams.creatorId"
            size="mini"
            style="width:140px"
            filterable
            clearable 
            reserve-keyword
            placeholder=" "
            :loading="loading">
              <el-option
                v-for="item in creatorOptions"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userId">
              </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="审批人" prop="reviewerId">
        <el-select
            v-model="queryParams.reviewerId"
            size="mini"
            style="width:140px"
            filterable
            clearable 
            reserve-keyword
            placeholder=" "
            :loading="loading">
              <el-option
                v-for="item in reviewerOptions"
                :key="item.userId"
                :label="item.nickName"
                :value="item.userId">
              </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="checkFlag">
        <el-select 
            v-model="queryParams.checkFlag" 
            placeholder=" " 
            clearable 
            style="width:140px"
            size="mini">
          <el-option
            v-for="dict in checkFlagOptions"
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


    <el-table 
        v-loading="loading" 
        :data="myorderList" 
        ref="table"
        highlight-current-row
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号"  align="center" prop="orderId" />
      <el-table-column label="买方公司名称"  align="center" prop="buyerName" />
      <el-table-column label="产品批次" align="center" prop="productId" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="数量" align="center" prop="productAmount" />
      <el-table-column label="单价" align="center" prop="productPrice" />
      <el-table-column label="总价" align="center" prop="productTotal" />
      <el-table-column label="审批人" align="center" prop="reviewerName" />
      <el-table-column label="审批状态" align="center" prop="checkFlag" :formatter="checkFlagFormat" />
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" >
        <el-row>
          <el-col :span="12">
            <el-form-item label="卖方公司:" prop="sellerName">
              <span>{{form.sellerName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="买方公司:" prop="buyerName">
              <span>{{form.buyerName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称:" prop="productName">
              <span>{{form.productName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品批次:" prop="productBatch">
              <span>{{form.productBatch}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品数量:" prop="productAmount">
              <span>{{form.productAmount}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品单价:" prop="productPrice">
              <span>{{form.productPrice}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品总价:" prop="productTotal">
              <span>{{form.productTotal}}</span>
            </el-form-item> 
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建人:" prop="creatorName">
              <span>{{form.creatorName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间:" prop="createTime" >
              <span>{{ parseTime(form.createTime) }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批人:" prop="reviewerName">
              <span>{{form.reviewerName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批时间:" prop="reviewTime" >
              <span>{{ parseTime(form.reviewTime) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
            type="primary"
            v-if="form.previousId != null"
            @click="handleFindOrder(form.previousId)"
          >上一次修改</el-button>
          <el-button
            type="primary"
            v-if="form.nextId != null"
            @click="handleFindOrder(form.nextId)"
          >下一次修改</el-button>
          <el-button type="primary" @click="submitForm" >确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder} from "@/api/dealer/invalidorder";
import { getOrder,ListCompany} from "@/api/dealer/myorder";
import { getListByName as getTypeList} from "@/api/dealer/type";
import { getUserListPerms } from "@/api/system/user";
export default {
  name: "invalidorder",
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
      myorderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      checkFlagOptions:[],
      checkStatusOptions:[],
      companyOptions: [],
      typeOptions:[],
      reviewerOptions:[],
      creatorOptions:[],
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        myorderCode: undefined,
        myorderName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        myorderName: [
          { required: true, message: "节点名称不能为空", trigger: "blur" }
        ],
        myorderCode: [
          { required: true, message: "节点编码不能为空", trigger: "blur" }
        ],
        myorderSort: [
          { required: true, message: "节点顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  computed: {

  },
  created() {
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("order_check_flag").then(response => {
      this.checkFlagOptions = response.data;
    });
    this.getDicts("order_check_status").then(response => {
      this.checkStatusOptions = response.data;
    });
    getUserListPerms("dealer:tocheck:list").then(response => {
      this.reviewerOptions = response.data;
    });
    getUserListPerms("dealer:myorder:list").then(response => {
      this.creatorOptions = response.data;
    });
    this.getList();
  },
  methods: {
    /** 查询节点列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.myorderList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 节点状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },

    checkFlagFormat(row, column) {
      return this.selectDictLabel(this.checkFlagOptions, row.checkFlag);
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 关闭对话框回调函数 */
    handleClose(){
      this.isView = false
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      this.isView = true
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看订单";
      });
    },

    /** 提交按钮 */
    submitForm: function() {
      if(this.isView){
        this.open = false;
        return
      }
    },
  
    listCompany(query){
      ListCompany(query).then(res=>{
        this.companyOptions = res
      })
    },
    listProduct(query){
      getTypeList(query).then(res=>{
        this.typeOptions = res.data
      })
    },
    handleFindOrder(id){
      getOrder(id).then(response => {
        this.form = response.data;
      });
      this.myorderList.forEach((order,index) => {
        if(order.orderId == id){
          this.$refs.table.setCurrentRow(order);
          return
        }
      })
      
    }
  }
};
</script>