<template>
  <div class="app-container">
   
    <el-card class="box-card">
      <div align="center">
      <el-form
        class="small-space"
        :model="tempInvoice"
        label-position="left"
        label-width="120px"
        style="width: 500px;height:850px; margin-left:50px;"
         :rules="rules"
          ref="tempInvoice"
      >    
        <el-form-item label="交易类型：" prop="orderType" >
            <el-radio-group v-model="tempInvoice.orderType" size="small">
              <el-radio label=0 border>销售</el-radio>
              <el-radio label=1 border>仓储</el-radio>
            </el-radio-group>
        </el-form-item>

        <el-form-item label="公司名：" prop="buyerId">
          <el-select  v-model="tempInvoice.buyerId" placeholder="请选择企业"  style="width:300px">
            <el-option  v-for="item in enterpriseNames" :key="item.id" :label="item.enterpriseName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

      <el-form-item label="商品名：" prop="productName">
        <el-select  v-model="tempInvoice.productName" placeholder="请选择农作物种类"  style="width:300px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
        <!-- <el-input  class="input-width"  style="width:300px" v-model="tempInvoice.productName" placeholder="请选择商品名"></el-input> -->
      </el-form-item>

      <el-form-item label="产品地块编号：" prop="findFarmlandRegionId">
        <el-select  v-model="tempInvoice.findFarmlandRegionId" placeholder="请选择地块"  style="width:300px">
            <el-option  v-for="item in farmlandRegion" :key="item.farmlandRegionId" :label="item.farmlandRegionId" :value="item.farmlandRegionId"></el-option>
          </el-select>
      </el-form-item>

      <el-form-item label="产品批次：" prop="productBatch">
          <el-select  v-model="tempInvoice.productBatch" placeholder="请选择种植批次"  style="width:300px">
            <el-option  v-for="item in batch" :key="item.id" :label="item.id" :value="item.id"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="产品ID" prop="productId">
        <el-input  class="input-width"  :disabled="true" style="width:300px" v-model="tempInvoice.findFarmlandRegionId+'-'+tempInvoice.productBatch" ></el-input>
      </el-form-item>

      <el-form-item label="产品数量(kg)：" prop="productAmount">
          <el-input-number v-model="tempInvoice.productAmount"  style="width:300px" :min="0" :max="1000000" ></el-input-number>
      </el-form-item>
      <el-form-item label="产品单价(元)：" prop="productPrice">
        
          <el-input-number v-model="tempInvoice.productPrice" :step="0.1"  style="width:300px" :min="0" :max="1000000" ></el-input-number>
      </el-form-item>
      <el-form-item label="产品总价(元)：" prop="totalPrice">
        <!-- <el-input v-model="tempInvoice.productTotal"></el-input> -->
          <el-input-number :disabled="true" v-model="tempInvoice.productAmount*tempInvoice.productPrice"  style="width:300px" :min="0" :step="0.1"  :max="1000000" ></el-input-number>
      </el-form-item>
 
     <el-form-item label="庄园名：">
        <div  v-text="tempInvoice.sellerName"></div>
        <!-- <el-input  class="input-width"  style="width:300px"  :disabled="true"  v-model="tempInvoice.sellerName" ></el-input> -->
      </el-form-item>

      <el-form-item label="庄园ID：">
        <div  v-text="tempInvoice.sellerId"></div>
        <!-- <el-input  class="input-width"   :disabled="true" style="width:300px" v-model="tempInvoice.sellerId"></el-input> -->
      </el-form-item>
      
      <el-form-item label="订单创建者：">
        <div  v-text="tempInvoice.orderCreator"></div>
        <!-- <el-input  class="input-width"  :disabled="true" style="width:300px" v-model="tempInvoice.sellerCreator" ></el-input> -->
      </el-form-item>

        <el-form-item label="审批人:"  prop="reviewerId">
          <el-select  v-model="tempInvoice.reviewerId" placeholder="请选择审批人"  style="width:300px">
            <el-option  v-for="item in checkUsers" :key="item.userName" :label="item.userName" :value="item.userName"></el-option>
          </el-select>
        </el-form-item>
  
  

      </el-form>
        <el-button @click="back" size="mini">返 回</el-button>
        <el-button  type="primary" size="mini" @click="createInvoice('tempInvoice')">创 建</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
import { createInvoice,findBuyer,getBatch,getCrops,getFarmlandRegionId,getEnterprise,getRealName,getCheckUser } from "@/api/manor/sale/invoice/add";

export default {
  data() {

      var checkOrderType = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("交易类型不能为空"))
        }
        callback();
      }
      
      var checkProductAmount = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("产品数量不能为空"))
        }
        callback();
      }

       var checkBuyerId = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("公司名不能为空"))
        }
        callback();
      }

         var checkProductName = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("商品不能为空"))
        }
        callback();
      }

      var checkFindFarmlandRegionId = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("地块不能为空"))
        }
        callback();
      }



       var checkProductBatch = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("批次号不能为空"))
        }
        callback();
      }

      var checkProductId = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("商品编号不能为空"))
        }
        callback();
      }

       var checkTotalPrice = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("总价不能为空"))
        }
        callback();
      }

      var checkProductPrice = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("单价不能为空"))
        }
        callback();
      }

         var checkReviewerId = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("审批人不能为空"))
        }
        callback();
      }

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
        isDeleted:'',
        orderCreateTime:'',
        orderUpdateTime:'',
        findFarmlandRegionId:'',
        orderCreator:"",
        reviewerId:""
        },
        checkUsers:[],
      farmlandRegion:[],
       rules: {
          orderType: [
            { validator: checkOrderType, trigger: 'blur' }
          ],
          productAmount: [
            { validator: checkProductAmount, trigger: 'blur' }
          ],
           productPrice: [
            { validator: checkProductPrice, trigger: 'blur' }
          ],
          
           productBatch: [
            { validator: checkProductBatch, trigger: 'blur' }
          ],
           findFarmlandRegionId: [
            { validator: checkFindFarmlandRegionId, trigger: 'blur' }
          ],
             productName: [
            { validator: checkProductName, trigger: 'blur' }
          ],
               buyerId: [
            { validator: checkBuyerId, trigger: 'blur' }
          ],

            reviewerId: [
            { validator: checkReviewerId, trigger: 'blur' }
          ],
         
           },
      enterpriseNames:[],
      crops:[],
      batch:[]
    };
  },
  created() {
    this.getFarmlandRegionId();
    this.getEnterprise();
    this.getBatch();
    this.getCrops();
    this.getRealName();
    this.getCheckUser();
  },
  methods: {

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



    back(){
     this.$router.push({path:'/myInvoice'});
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

    getBatch() {
      getBatch().then(response => {
        const data = response.data
        this.batch = data;
        }
      );
    },


    createInvoice(formName) {
      this.tempInvoice.productId = this.tempInvoice.findFarmlandRegionId +"-"+ this.tempInvoice.productBatch;
      this.tempInvoice.productTotal=this.tempInvoice.productAmount * this.tempInvoice.productPrice;

        this.$refs[formName].validate((valid) => {
            if (valid) {
              
                createInvoice(
                  this.tempInvoice
                ).then(response => {
                  const data = response.data
                    alert(data)
                  }
                );
              
          this.$router.push({path:'/myInvoice'});
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },


  }
};
</script>

