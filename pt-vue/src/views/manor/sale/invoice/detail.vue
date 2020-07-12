<template>
  <div class="app-container">
   
    <el-card class="box-card">
      <div align="center">
      <el-form
        class="small-space"
        :model="tempInvoice"
        label-position="right"
        label-width="130px"
        style="width: 1000px;height:600px; margin-left:200px;"
         :rules="rules"
      >    

        <el-row>
          <el-col :span="12"><div class="grid-content bg-purple">
              <el-form-item label="订单编号:" align="left">
                <div    align="left">
                  <el-tag v-text="tempInvoice.orderId" ></el-tag>
                </div>
              </el-form-item>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-light">

        <el-form-item label="交易类型:"  align="left">  
          <div v-if="tempInvoice.orderType == 0" align="left">
            <el-tag>销售交易 </el-tag></div>
          <div v-if="tempInvoice.orderType == 1"  align="left">
            <el-tag>仓储交易 </el-tag></div>
        </el-form-item>
            </div></el-col>
        </el-row>

        <el-row>
          <el-col :span="12"><div class="grid-content bg-purple">
            <el-form-item label="公司编号:" >
              <div v-text="tempInvoice.buyerId" align="left"></div>
            </el-form-item>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-light">
            <el-form-item label="公司名称:" >
              <div v-text="tempInvoice.buyerName" align="left"></div>
            </el-form-item>
            </div></el-col>
        </el-row>


        <el-row>
          <el-col :span="12"><div class="grid-content bg-purple">
                  <el-form-item label="商品名:">
          <div v-text="tempInvoice.productName" align="left"></div>
      </el-form-item>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-light">
       <el-form-item label="产品编号:">
          <div v-text="tempInvoice.findFarmlandRegionId+'-'+tempInvoice.productBatch" align="left"></div>
      </el-form-item>
            </div></el-col>
        </el-row>



            <el-row>
        <el-col :span="12"><div >
                <el-form-item label="产品数量(kg):">
          <div v-text="tempInvoice.productAmount" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="产品单价(元):">
          <div v-text="tempInvoice.productPrice" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>


      <el-row>
        <el-col :span="12"><div >
               <el-form-item label="产品总价(元):">
          <div v-text="tempInvoice.productAmount*tempInvoice.productPrice" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="庄园名:">
          <div v-text="tempInvoice.sellerName" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>

      <el-row>
        <el-col :span="12"><div >
                <el-form-item label="庄园编号:">
          <div v-text="tempInvoice.sellerId" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="审核状态:">
          
          <div v-if="tempInvoice.checkFlag == 0" style="color:#800000" align="left" > 待送审</div>
          <div v-if="tempInvoice.checkFlag == 1" style="color:#0000FF" align="left"> 待审核</div>
          <div v-if="tempInvoice.checkFlag == 2" style="color:#008800" align="left">  审核通过</div>
          <div v-if="tempInvoice.checkFlag == 3" style="color:#FF0000" align="left"> 审核未通过</div>
          <div v-if="tempInvoice.checkFlag == 4" style="color:#008800" align="left">  审核通过</div>

    
      </el-form-item>
          </div></el-col>
      </el-row>

            <el-row>
        <el-col :span="12"><div >
               <el-form-item label="创建者用户名:">
          <div v-text="tempInvoice.orderCreator" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="创建者姓名:">
          <div v-text="tempInvoice.creatorName" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>

                  <el-row>
        <el-col :span="12"><div >
               <el-form-item label="审核者用户名:">
          <div v-text="tempInvoice.reviewerId" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="审核者姓名:">
          <div v-text="tempInvoice.reviewerName" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>


            <el-row>
        <el-col :span="12"><div >
                <el-form-item label="创建日期:">
          <div v-text="tempInvoice.createTime" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div v-if= "(!tempInvoice.previousId =='')" >
                <el-form-item label="修改前发票编号:">
          <div v-text="tempInvoice.previousId" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>

      </el-form>
        <el-button @click="back"  size="mini" >返 回</el-button>
        <el-button @click="update()" type="primary"  v-if="tempInvoice.checkFlag == 0" size="mini" >编 辑</el-button>
        <el-button @click="updateNew()" type="primary"  v-if="(tempInvoice.checkFlag == 2)" size="mini" >在此基础上新建</el-button>
        <el-button @click="getPrevious()" type="info" v-if= "(!tempInvoice.previousId =='')" size="mini" >查看修改前发票</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
import { getParams } from "@/api/manor/sale/invoice/detail";

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
        checkFlag:"",
        previousId:""
        },
      farmlandRegion:[],
    rules:{
    },
      enterpriseNames:[],
      crops:[],
      batch:[],
      id:{
        id:""
      }
    };
  },
  created() {
    this.getParams();
  },
  methods: {


     update(){
        var a = this.tempInvoice.orderId;
          this.$router.push({path:'/detail/invoiceUpdate',
          query:{id:a}
        });
      },



    back(){
     this.$router.push({path:'/myInvoice'});
    },

  
   getParams() {
    this.id.id=this.$route.query.id
      getParams(
        this.id
      ).then(response => {
        const data = response.data
        this.tempInvoice.orderId = data.orderId;
          this.tempInvoice.orderCreator = data.orderCreator;
          this.tempInvoice.sellerNumber =data.sellerNumber;
          this.tempInvoice.sellerName = data.sellerName;
          this.tempInvoice.buyerId = data.buyerId;
          this.tempInvoice.buyerName = data.buyerName;
          this.tempInvoice.buyerNumber = data.buyerNumber;
          this.tempInvoice.productId = data.productId;
          this.tempInvoice.productName = data.productName;
          this.tempInvoice.productAmount = data.productAmount;
          this.tempInvoice.productPrice = data.productPrice;
          this.tempInvoice.productTotal = data.productTotal;
          this.tempInvoice.createTime=data.createTime;
          this.tempInvoice.orderUpdateTime = data.orderUpdateTime;
          this.tempInvoice.orderType = data.orderType;
          var arr = data.productId.split("-");
          this.tempInvoice.findFarmlandRegionId = arr[0];
          this.tempInvoice.productBatch = arr[1];
          this.tempInvoice.beforeInvoiceId = data.beforeInvoiceId;
          this.tempInvoice.creatorName = data.creatorName;
          this.tempInvoice.reviewerName = data.reviewerName;
          this.tempInvoice.reviewerId = data.reviewerId;
          this.tempInvoice.checkFlag = data.checkFlag;
          this.tempInvoice.previousId = data.previousId;
        }
      );
    },
  }


  
};
</script>

