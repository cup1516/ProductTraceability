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
                  v-model="tempFind.buyerName"
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
          <el-select  v-model="tempFind.productName" placeholder="请选择农作物种类"  style="width:170px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
          </el-form-item>

          <el-form-item label="审核人:">
            <el-select  v-model="tempFind.reviewerName" placeholder="请选择审核人"  style="width:170px" >
              <el-option  v-for="item in staffs" :key="item.userName" :label="item.userName" :value="item.userName"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="开始时间:" >
              <el-date-picker
              style="width:170px"
                type="date"
                   v-model="tempFind.stime"
                placeholder="选择日期">
              </el-date-picker>
          </el-form-item>
                   <el-form-item label="截止时间:" label-width="140px">
              <el-date-picker
               v-model="tempFind.etime"
                type="date"
                 style="width:170px"
                placeholder="选择日期">
              </el-date-picker>
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
      <el-table-column align="center" prop="creatorName" label="创建人" width="200"></el-table-column>
      <el-table-column align="center" prop="reviewerName" label="审核人" width="100"></el-table-column>
      <el-table-column align="center" prop="reviewTime" label="审核时间" width="150"></el-table-column>
      <el-table-column align="center" prop="status" label="目前状态" width="100">
        <template slot-scope="scope">
          <div v-if="scope.row.checkFlag == 2" style="color:#008800"> 审核通过</div>
          <div v-if="scope.row.checkFlag == 3" style="color:#FF0000"> 审核未通过</div>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="300" >
        <template slot-scope="scope">
          <el-button
            type="info"
           icon="el-icon-tickets"
            @click="detail(scope.$index)"
            style="font-size: 9px;"
            size="mini"
          >查看</el-button>

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


import { getCrops } from "@/api/manor/order/add";
import { list ,find} from "@/api/manor/order/finalReviewOrder";
import {getStaff} from "@/api/manor/order/reviewOrder";
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
        buyerName:"",
        productName:"",
        stime:"",
        etime:"",
        status:'',
        reviewerName:""
      },
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
        reviewTime:"",
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
      crops:[],
      staffs:[],
      companyOptions:[]
    };
  },
  created() {
    this.getList();
    this.getCrops();
    this.getStaff();
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

    getStaff(){
      getStaff().then(response => {
        const data = response.data
        this.staffs = data;
        }
      );      
    },

  detail($index){
      this.$router.push({path:'/detail/orderDetail',
       query:{id:this.list[$index].orderId}
     });
   },
    
    handleResetSearch(){
      this.tempFind.orderId="",
      this.tempFind.buyerName="",
      this.tempFind.drawer="",
      this.tempFind.stime="",
      this.tempFind.etime="",
      this.tempFind.productName="",
      this.tempFind.checkFlag=""
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
          "buyerName":this.tempFind.buyerName,
          "productName":this.tempFind.productName,
          "orderId":this.tempFind.orderId,
          "stime":this.tempFind.stime,
          "etime":this.tempFind.etime,
          "reviewerName":this.tempFind.reviewerName
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


 getEnterprise() {
      getEnterprise().then(response => {
        const data = response.data
           this.enterpriseNames = data; 
        }
      );
    },

      getFarmlandRegionId() {
      getFarmlandRegionId().then(response => {
        const data = response.data
        this.farmlandRegion = data;
        }
      );
    },

    getCrops() {
      getCrops().then(response => {
        const data = response.data
        this.crops = data;
        }
      );
    },

  }
};
</script>

