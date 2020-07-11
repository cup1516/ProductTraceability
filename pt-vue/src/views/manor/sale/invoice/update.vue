<template>
  <div class="app-container">
   
    <el-card class="box-card">
      <div align="center">
      <el-form
        class="small-space"
        :model="tempInvoice"
        label-position="right"
        label-width="120px"
        style="width: 500px;height:850px; margin-left:50px;"
         :rules="rules"
      >    
        <el-form-item label="交易类型：" prop="orderType">
              <el-radio size="mini"  v-model="tempInvoice.orderType" label=0 >销售</el-radio>
              <el-radio size="mini" v-model="tempInvoice.orderType" label=1 >仓储</el-radio>
        </el-form-item>


        <el-form-item label="公司名：" >
          <el-select  v-model="tempInvoice.buyerId" placeholder="请选择企业"  style="width:300px">
            <el-option  v-for="item in enterpriseNames" :key="item.id" :label="item.enterpriseName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

      <el-form-item label="商品名：">
        <el-select  v-model="tempInvoice.productName" placeholder="请选择农作物种类"  style="width:300px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
      </el-form-item>

      <el-form-item label="产品地块编号：">
        <el-select  v-model="tempInvoice.findFarmlandRegionId" placeholder="请选择地块"  style="width:300px">
            <el-option  v-for="item in farmlandRegion" :key="item.farmlandRegionId" :label="item.farmlandRegionId" :value="item.farmlandRegionId"></el-option>
          </el-select>
      </el-form-item>

      <el-form-item label="产品批次：">
          <el-select  v-model="tempInvoice.productBatch" placeholder="请选择种植批次"  style="width:300px">
            <el-option  v-for="item in batch" :key="item.id" :label="item.id" :value="item.id"></el-option>
          </el-select>
      </el-form-item>

      <el-form-item label="产品ID">
        <el-input  class="input-width"  :disabled="true" style="width:300px" v-model="tempInvoice.findFarmlandRegionId+'-'+tempInvoice.productBatch" ></el-input>
      </el-form-item>

      <el-form-item label="产品数量(kg)：">
          <el-input-number v-model="tempInvoice.productAmount"  style="width:300px" :min="0" :max="1000000" ></el-input-number>
      </el-form-item>
      <el-form-item label="产品单价(元)：">
          <el-input-number v-model="tempInvoice.productPrice" :step="0.1"  style="width:300px" :min="0" :max="1000000" ></el-input-number>
      </el-form-item>
      <el-form-item label="产品总价(元)：">
          <el-input-number :disabled="true" v-model="tempInvoice.productAmount*tempInvoice.productPrice"  style="width:300px" :min="0" :step="0.1"  :max="1000000" ></el-input-number>
      </el-form-item>
 
      <el-form-item label="庄园名：">
        <div  v-text="tempInvoice.sellerName"></div>
      </el-form-item>

      <el-form-item label="庄园ID：">
        <div  v-text="tempInvoice.sellerId"></div>
      </el-form-item>
      
      <el-form-item label="订单创建者：">
        <div  v-text="tempInvoice.orderCreator"></div>
      </el-form-item>
  

        <el-form-item label="审批人:"  prop="reviewerId">
          <el-select  v-model="tempInvoice.reviewerId" placeholder="请选择审批人"  style="width:300px">
            <el-option  v-for="item in checkUsers" :key="item.userName" :label="item.userName" :value="item.userName"></el-option>
          </el-select>
        </el-form-item>

      </el-form>
        <el-button size="mini"    @click="back">返 回</el-button>
        <el-button  v-if="tempInvoice.checkFlag == 0" size="mini" type="primary" @click="update">提 交</el-button>
        <el-button  v-if="(tempInvoice.checkFlag == 2)||(tempInvoice.checkFlag == 3)" size="mini" type="primary" @click="updateNew">提 交</el-button>
      </div>

    </el-card>
  </div>
</template>
<script>
import { getParams,update } from "@/api/manor/sale/invoice/update";
import { createInvoice,findBuyer,getBatch,getCrops,getFarmlandRegionId,getEnterprise,getRealName,getCheckUser } from "@/api/manor/sale/invoice/add";


export default {
  data() {
    return {
      tempInvoice:{
    sellerNumber:"",
        sellerName:"优鲜庄园",
        sellerId:"CTFGUH62345",
        sellerCreator:"",
        buyerId:"",
        buyerName:'',
        buyerNumber:'',
        productId:'',
        productName:'',
        productBatch:'',
        productAmount:'',
        productPrice:'',
        productTotal:'',
        orderType:'',
        isDeleted:0,
        orderCreateTime:'',
        orderUpdateTime:'',
        orderId:"",
        findFarmlandRegionId:"",
        beforeInvoiceId:'',
        reviewerId:"",
        createTime:"",
        orderCreator:"",
        creatorName:"",
        reviewerName:"",
        checkFlag:""
        },
        id:{
          id:""
        },
      farmlandRegion:[],
    rules:{
    },
      enterpriseNames:[],
      crops:[],
      batch:[],
       checkUsers:[],
    };
  },
  created() {
    this.getFarmlandRegionId();
    this.getEnterprise();
    this.getBatch();
    this.getCrops();
    this.getParams();
    this.getCheckUser();
  },
  methods: {

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

    getBatch() {
      getBatch().then(response => {
        const data = response.data
        this.batch = data;
        }
      );
    },


 
  
    back(){
     this.$router.push({path:'/myInvoice'});
    },

    
    update() {
      //修改经销商信息
      this.tempInvoice.productTotal=this.tempInvoice.productAmount * this.tempInvoice.productPrice;
      this.tempInvoice.productId = this.tempInvoice.findFarmlandRegionId + '-' +this.tempInvoice.productBatch
     
    //  this.api({
    //     url: "myInvoice/update",
    //     method: "post",
    //     data: this.tempInvoice
    //   }).then(() => {
    //     alert("修改成功！")
    //  this.$router.push({path:'/fruitEnterprises/myInvoice'});
    //   });

      update(
        this.tempInvoice
      ).then(response =>{
        alert("修改成功！")
     this.$router.push({path:'/myInvoice'});
      })
    },



    getCheckUser() {
      getCheckUser().then(response => {
        const data = response.data

        this.checkUsers = data;    
        }
      );
    },

    getRealName() {
      getRealName().then(response => {
        const data = response.data
          this.tempInvoice.orderCreator= data.sysUser.userName
        }
      );
    },

    


    
   getParams() {
       this.id.id=this.$route.query.id

      getParams(
        this.id
      ).then(response => {
        const data = response.data
       this.tempInvoice.orderId = data.orderId;
          this.tempInvoice.sellerNumber =data.sellerNumber;
          this.tempInvoice.sellerName = data.sellerName;
          this.tempInvoice.orderCreator = data.orderCreator;
          this.tempInvoice.buyerId = data.buyerId;
          this.tempInvoice.buyerName = data.buyerName;
          this.tempInvoice.buyerNumber = data.buyerNumber;
          this.tempInvoice.productId = data.productId;
          this.tempInvoice.productName = data.productName;
          this.tempInvoice.productAmount = data.productAmount;
          this.tempInvoice.productPrice = data.productPrice;
          this.tempInvoice.productTotal = data.productTotal;
          this.tempInvoice.reviewerId = data.reviewerId;
         
          this.tempInvoice.orderType = data.orderType;
          var arr = data.productId.split("-");
          this.tempInvoice.findFarmlandRegionId = arr[0];
          this.tempInvoice.productBatch = arr[1];
    
          this.tempInvoice.checkFlag = data.checkFlag;  
        }
      );
    },
  

    // getParams(){
    //    this.api({
    //       url:"invoice/get",
    //       method:"get",
    //       params:{
    //        id:this.$route.query.id
    //        }
    //     }).then(data => {
    //       this.tempInvoice.orderId = data.orderId;
    //       this.tempInvoice.sellerNumber =data.sellerNumber;
    //       this.tempInvoice.sellerName = data.sellerName;
    //       this.tempInvoice.orderCreator = data.orderCreator;
    //       this.tempInvoice.buyerId = data.buyerId;
    //       this.tempInvoice.buyerName = data.buyerName;
    //       this.tempInvoice.buyerNumber = data.buyerNumber;
    //       this.tempInvoice.productId = data.productId;
    //       this.tempInvoice.productName = data.productName;
    //       this.tempInvoice.productAmount = data.productAmount;
    //       this.tempInvoice.productPrice = data.productPrice;
    //       this.tempInvoice.productTotal = data.productTotal;
    //       this.tempInvoice.reviewerId = data.reviewerId;
         
    //       this.tempInvoice.orderType = data.orderType;
    //       var arr = data.productId.split("-");
    //       this.tempInvoice.findFarmlandRegionId = arr[0];
    //       this.tempInvoice.productBatch = arr[1];
    
    //       this.tempInvoice.checkFlag = data.checkFlag;  
    //     })
    // },
  }

};
</script>

