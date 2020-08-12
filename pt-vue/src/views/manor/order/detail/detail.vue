<template>
  <div class="app-container">
   
    <el-card class="box-card">
      <div align="center">
      <el-form
        class="small-space"
        :model="tempOrder"
        label-position="right"
        label-width="130px"
        style="width: 1000px;height:600px; margin-left:200px;"
         :rules="rules"
      >    

        <el-row>
          <el-col :span="12"><div class="grid-content bg-purple">
              <el-form-item label="订单编号:" align="left">
                <div    align="left">
                  <el-tag v-text="tempOrder.orderId" ></el-tag>
                </div>
              </el-form-item>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-light">
              <el-form-item label="产品编号:" align="left">
                  <div v-text="tempOrder.productId" align="left"></div>
              </el-form-item>
            </div></el-col>
        </el-row>

        <el-row>
          <el-col :span="12"><div class="grid-content bg-purple">
            <el-form-item label="公司编号:" >
              <div v-text="tempOrder.buyerId" align="left"></div>
            </el-form-item>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-light">
            <el-form-item label="公司名称:" >
              <div v-text="tempOrder.buyerName" align="left"></div>
            </el-form-item>
            </div></el-col>
        </el-row>


        <el-row>
          <el-col :span="12"><div class="grid-content bg-purple">
                  <el-form-item label="商品名:">
          <div v-text="tempOrder.productName" align="left"></div>
      </el-form-item>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-light">
       <el-form-item label="产品批次:">
          <div v-text="tempOrder.productBatch" align="left"></div>
      </el-form-item>
            </div></el-col>
        </el-row>



            <el-row>
        <el-col :span="12"><div >
                <el-form-item label="产品数量(kg):">
          <div v-text="tempOrder.productAmount" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="产品单价(元):">
          <div v-text="tempOrder.productPrice" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>


      <el-row>
        <el-col :span="12"><div >
               <el-form-item label="产品总价(元):">
          <div v-text="tempOrder.productAmount*tempOrder.productPrice" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="庄园名:">
          <div v-text="tempOrder.sellerName" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>

      <el-row>
        <el-col :span="12"><div >
                <el-form-item label="庄园编号:">
          <div v-text="tempOrder.sellerId" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="审核状态:">
          <div v-if="tempOrder.checkFlag == 0" style="color:#800000" align="left" > 待送审</div>
          <div v-if="tempOrder.checkFlag == 1" style="color:#0000FF" align="left"> 待审核</div>
          <div v-if="tempOrder.checkFlag == 2" style="color:#008800" align="left"> 审核通过</div>
          <div v-if="tempOrder.checkFlag == 3" style="color:#FF0000" align="left"> 审核未通过</div>
      </el-form-item>
          </div></el-col>
      </el-row>

            <el-row>
        <el-col :span="12"><div >
               <el-form-item label="创建者ID:">
          <div v-text="tempOrder.creatorId" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="创建者姓名:">
          <div v-text="tempOrder.creatorName" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>

                  <el-row>
        <el-col :span="12"><div >
               <el-form-item label="审核者ID:">
          <div v-text="tempOrder.reviewerId" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                <el-form-item label="审核者姓名:">
          <div v-text="tempOrder.reviewerName" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>


            <el-row>
        <el-col :span="12"><div >
                <el-form-item label="创建日期:">
          <div v-text="tempOrder.createTime" align="left"></div>
      </el-form-item>
          </div></el-col>
        <el-col :span="12"><div v-if= "(!tempOrder.previousId =='')" >
                <el-form-item label="修改前发票编号:">
          <div v-text="tempOrder.previousId" align="left"></div>
      </el-form-item>
          </div></el-col>
      </el-row>

      
            <el-row>
        <el-col :span="12"><div v-if= "(!tempOrder.nextId =='')" >
            <el-form-item label="修改前发票编号:">
              <div v-text="tempOrder.nextId" align="left"></div>
          </el-form-item>
          </div></el-col>
          <el-col :span="12"><div>
            <el-form-item label="确认状态:">
              <div v-if="tempOrder.checkStatus == 0" style="color:#800000" align="left" > 未送确认</div>
              <div v-if="tempOrder.checkStatus == 1" style="color:#0000FF" align="left"> 待确认</div>
              <div v-if="tempOrder.checkStatus == 2" style="color:#008800" align="left"> 确认通过</div>
              <div v-if="tempOrder.checkStatus == 3" style="color:#FF0000" align="left"> 确认未通过</div>
          </el-form-item>
          </div></el-col>
      </el-row>

      </el-form>
        <el-button @click="update()" type="primary"  v-if="tempOrder.checkFlag == 0" size="mini" >编 辑</el-button>
        <el-button @click="update()" type="primary"  v-if="(tempOrder.checkFlag == 3)" size="mini" >在此基础上新建</el-button>
        <el-button @click="getPrevious()" v-if= "(!tempOrder.previousId =='')" type="info"size="mini" >查看修改前发票</el-button>
        <el-button @click="getNext()" v-if= "(!tempOrder.nextId =='')" type="info"size="mini" >查看修改后发票</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
import { getParams } from "@/api/manor/order/detail/detail";

export default {
  data() {
    return {
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
      id:{
        id:""
      }
    };
  },
  created() {
    this.getParams();
  },
  methods: {

      getPrevious(){
        this.$router.push({path:'/detail/orderDetail',
           query:{id:this.tempOrder.previousId}
         });
         this.getParams();
      },

      getNext(){
        this.$router.push({path:'/detail/orderDetail',
           query:{id:this.tempOrder.nextId}
         });
         this.getParams();
      },


     update(){
        var a = this.tempOrder.orderId;
          this.$router.push({path:'/detail/updateOrder',
          query:{id:a}
        });
      },
   getParams() {
    this.id.id=this.$route.query.id
      getParams(
        this.id
      ).then(response => {
        const data = response.data
        this.tempOrder.orderId = data.orderId;
          this.tempOrder.orderCreator = data.orderCreator;
          this.tempOrder.sellerId =data.sellerId;
          this.tempOrder.sellerName = data.sellerName;
          this.tempOrder.buyerId = data.buyerId;
          this.tempOrder.buyerName = data.buyerName;
          this.tempOrder.buyerId = data.buyerId;
          this.tempOrder.productId = data.productId;
          this.tempOrder.productName = data.productName;
          this.tempOrder.productAmount = data.productAmount;
          this.tempOrder.productPrice = data.productPrice;
          this.tempOrder.productTotal = data.productTotal;
          this.tempOrder.productBatch = data.productBatch;
          this.tempOrder.createTime=data.createTime;
          this.tempOrder.orderUpdateTime = data.orderUpdateTime;
          this.tempOrder.orderType = data.orderType;
          this.tempOrder.productBatch = data.productBatch;
          this.tempOrder.creatorName = data.creatorName;
          this.tempOrder.reviewerName = data.reviewerName;
          this.tempOrder.reviewerId = data.reviewerId;
          this.tempOrder.checkFlag = data.checkFlag;
          this.tempOrder.previousId = data.previousId;
          this.tempOrder.creatorId = data.creatorId;
          this.tempOrder.nextId = data.nextId;
          this.tempOrder.checkStatus = data.checkStatus;
        }
      );
    },
  },

};
 
</script>

