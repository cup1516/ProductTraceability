<template>
  <div class="app-container">
    <div class="filter-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          @click="find()"

         size="mini"
          icon="el-icon-search"
          >搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="mini"
           icon="el-icon-refresh"
         >重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true"  size="small" label-width="140px" :model="tempFind">
          <el-form-item label="票据编号:" >
            <el-input  class="input-width" placeholder="票据编号" v-model="tempFind.orderId"></el-input>
          </el-form-item>
          <el-form-item label="企业名:" width="180">
              <el-select
                  v-model="tempFind.sellerName"
                  style="width:170px"
                  filterable
                  remote
                  clearable 
                  reserve-keyword
                  placeholder=" "
                  @change="setBuyerName"
                  :remote-method="listCompany"
                  :loading="loading">
                  <el-option
                    v-for="item in companyOptions"
                    :key="item.companyId"
                    :label="item.companyName"
                    :value="item.companyName">
                  </el-option>
                </el-select>
          </el-form-item>
          <el-form-item label="商品名:">
            <el-input v-model="tempFind.productName" placeholder="请输入商品名"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
        <el-card class="operate-container" shadow="never" style="height:90px">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
        
          <el-button
            icon="el-icon-refresh"  
            @click="getList"
            size="mini"
             style="float:right;margin-right: 15px"
          >刷新</el-button>
    </el-card>
    </div>

    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span>{{scope.$index+1 }}</span>
        </template>
      </el-table-column>

  
      <el-table-column align="center" prop="orderId" label="票据编号" width="270"></el-table-column>
      <el-table-column align="center" prop="buyerName" label="企业名" width="200"></el-table-column>
      <el-table-column align="center" prop="productName" label="商品名" width="100"></el-table-column>
      <el-table-column align="center" prop="reviewTime" label="审核时间" width="150"></el-table-column>
      <el-table-column align="center" prop="productTotal" label="总金额(元)" width="150"></el-table-column>
      <el-table-column align="center" label="操作" width="300" >
        <template slot-scope="scope">
          <el-button
            type="info"
           icon="el-icon-tickets"
            @click="detail(scope.$index)"
            style="font-size: 9px;"
            size="mini"
          >查看</el-button>

           <el-button
            type="success"
              size="mini"
            style="font-size: 9px;"
            @click="pass(scope.$index)"

          >通过</el-button>
        
          <el-button
            type="danger"
            style="font-size: 9px;"
            size="mini"
          @click="checkBack(scope.$index)"
          >打回</el-button>

        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>
<script>

import { list ,checkBack,pass,find} from "@/api/manor/order/checkOrder";
import{ListCompany}from "@/api/manor/order/add"

export default {
  data() {
    return {
        dialogFormVisible1: false,
        formLabelWidth: '120px',
      total: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        name: ""
      },
      tempFind:{
        orderId:"",
        sellerName:"",
        productName:"",
      },
      companyOptions:[],
      tempOrder:{
        productBatch:"",
        orderId:"",
        sellerId:"",
        sellerName:"",
        buyerId:"",
        buyerName:"",
        productId:"",
        productName:"",
        productAmount:"",
        productPrice:"",
        productTotal:"",
        creatorId:"",
        creatorName:"",
        createTime:"",
        reviewerId:"",
        reviewerName:"",
        reviewerSuggestions:"",
        reviewerTime:"",
        checkerId:"",
        checkerName:"",
        checkTime:"",
        checkerSuggestions:"",
        nextId:"",
        previousId:"",
        checkFlag:"",
        delFlag:"",
        checkStatus:"",
        },
    };
  },
  created() {
    this.getList();

  },
  methods: {


     listCompany(query){
      ListCompany(query).then(res=>{
        this.companyOptions = res
      })
    },

    setBuyerName(val){
      this.companyOptions.forEach(company => {
        if(company.companyId == val){
          this.form.buyerName = company.companyName
          return
        }
      })
    },
  
    getList() {
      this.listLoading = true;
      list(this.listQuery).then(response => {
        
        const data = response.data
        this.listLoading = false;
        this.list = data.content;
        this.total = data.totalElements;
        this.pageNum = data.number+1;
        this.pageRow = data.size;
        }
      );
    },

    getStaff(){
      getStaff().then(response => {
        const data = response.data
        this.staffs = data;
        }
      );      
    },


     pass($index) {
      this.tempOrder.orderId = this.list[$index].orderId;
      pass(this.tempOrder).then(response => {  
      this.getList();
        }
      );
    },

    checkBack($index) {
      this.tempOrder.orderId = this.list[$index].orderId;
      checkBack(this.tempOrder).then(response => {  
      this.getList();
        }
      );
    },




    detail($index){
      this.$router.push({path:'/detail/orderDetail',
       query:{id:this.list[$index].orderId}
     });
   },

    
  update($index){
      this.$router.push({path:'/fruitEnterprises/updateInvoice',
       query:{id:this.list[$index].orderId}
     });
   },

          
    add(){
     this.$router.push({path:'/fruitEnterprises/addInvoice'});
    },

    getCrops() {
      getCrops().then(response => {
        const data = response.data
        this.crops = data;
        }
      );
    },

    handleResetSearch(){
      this.tempFind.orderId="",
      this.tempFind.sellerName="",
      this.tempFind.productName=""
    },



    msgList(params) {
      return new Promise((resolve, reject) => {
        axios
          .post(api.MSG_TOP_TEN, { params })
          .then(
            response => {
              resolve(response.data);
            },
            err => {
              resolve(defaultValue.msgList);
            }
          )
          .catch(error => {
            resolve(defaultValue.msgList);
          });
      });
    },
    menuList(params) {
      return new Promise((resolve, reject) => {
        axios
          .get(api.SYS_MENU_LIST, { params })
          .then(
            response => {
              resolve(response.data);
            },
            err => {
              resolve(defaultValue.menuList);
            }
          )
          .catch(error => {
            resolve(defaultValue.menuList);
          });
      });
    },
    handleSizeChange(val) {
      //改变每页数量
      this.pageRow = val;
      this.listQuery.pageRow = val;
      this.getList();
      //this.handleFilter();
    },
    handleCurrentChange(val) {
      //改变页码
      this.pageNum = val;
      this.listQuery.pageNum = val;
      this.getList();
    },
    
    getIndex($index) {
      //表格序号
      return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1;
    },



    find() {
      this.listLoading = true;
      find(
        {
          "pageNum":this.listQuery.pageNum,
          "pageRow":this.listQuery.pageRow,
          "sellerName":this.tempFind.sellerName,
          "productName":this.tempFind.productName,
          "orderId":this.tempFind.orderId,
        }
      ).then(response => {
        const data = response.data
        this.list = data.content;
        this.total = data.totalPages;
        this.pageNum = data.number;
        this.pageRow = data.size;
        this.listLoading = false;
        }
      );
    },


    isDelete($index) {
      this.tempInvoice.orderId = this.list[$index].orderId;
      //修改商品信息
      this.api({
        url: "invoice/delete",
        method: "post",
        data: this.tempInvoice
      }).then(() => {
        this.getList();
      });
    },


    invalid($index){
      this.tempInvoice.orderId = this.list[$index].orderId;
      this.api({
        url: "invoice/invalid",
        method: "post",
        data: this.tempInvoice
      }).then(() => {
        this.getList();

      });
    }


  }
};
</script>

