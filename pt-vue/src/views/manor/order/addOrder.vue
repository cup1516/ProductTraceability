<template>
  <div class="app-container">
   
    <el-card class="box-card">
      <div align="center">
      <el-form
        class="small-space"
        :model="tempOrder"
        label-position="left"
        label-width="120px"
        style="width: 500px;height:850px; margin-left:50px;"
         :rules="rules"
          ref="tempOrder"
      >    

        <el-form-item label="公司名：" prop="buyerId">
           <el-select
                  v-model="tempOrder.buyerId"
                  style="width:300px"
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
                    :value="item.companyId">
                  </el-option>
                </el-select>
        </el-form-item>

      <el-form-item label="商品名：" prop="productName">
        <el-select  v-model="tempOrder.productId" placeholder="请选择农作物种类"  style="width:300px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.id"></el-option>
          </el-select>
        <!-- <el-input  class="input-width"  style="width:300px" v-model="tempInvoice.productName" placeholder="请选择商品名"></el-input> -->
      </el-form-item>

      <el-form-item label="产品地块编号：" prop="findFarmlandRegionId">
        <el-select  v-model="tempOrder.findFarmlandRegionId" placeholder="请选择地块"  style="width:300px">
            <el-option  v-for="item in farmlandRegion" :key="item.farmlandRegionId" :label="item.farmlandRegionId" :value="item.farmlandRegionId"></el-option>
          </el-select>
      </el-form-item>

      <el-form-item label="种植批次：" prop="plantingBatch">
          <el-select  v-model="tempOrder.plantingBatch" placeholder="请选择种植批次"  style="width:300px">
            <el-option  v-for="item in batch" :key="item.id" :label="item.id" :value="item.id"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="产品批次" prop="productBatch">
        <el-input  class="input-width"  :disabled="true" style="width:300px" v-model="tempOrder.findFarmlandRegionId+'-'+tempOrder.plantingBatch" ></el-input>
      </el-form-item>

      <el-form-item label="产品数量(kg)：" prop="productAmount">
          <el-input-number v-model="tempOrder.productAmount"  style="width:300px" :min="0" :max="1000000" ></el-input-number>
      </el-form-item>
      <el-form-item label="产品单价(元)：" prop="productPrice">
        
          <el-input-number v-model="tempOrder.productPrice" :step="0.1"  style="width:300px" :min="0" :max="1000000" ></el-input-number>
      </el-form-item>
      <el-form-item label="产品总价(元)：" prop="totalPrice">
          <el-input-number :disabled="true" v-model="tempOrder.productAmount*tempOrder.productPrice"  style="width:300px" :min="0" :step="0.1"  :max="1000000" ></el-input-number>
      </el-form-item>
 
     <el-form-item label="庄园名：">
        <div  v-text="tempOrder.sellerName"></div>
      </el-form-item>


      
      <el-form-item label="订单创建者：">
        <div  v-text="tempOrder.orderCreator"></div>
      </el-form-item>

        <el-form-item label="审批人:"  prop="reviewerId">
          <el-select  v-model="tempOrder.reviewerId" placeholder="请选择审批人"  style="width:300px">
            <el-option  v-for="item in checkUsers" :key="item.userName" :label="item.userName" :value="item.userId"></el-option>
          </el-select>
        </el-form-item>
  
      </el-form>
        <el-button @click="back" size="mini">返 回</el-button>
        <el-button  type="primary" size="mini" @click="create('tempOrder')">创 建</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>

import {getCheckUser,getRealName,getCompanyInfo,getCompanyList,getCompanyName,add,ListCompany,getBatch,getCrops,getFarmlandRegionId} from "@/api/manor/order/add";
import store from '../../../store/modules/portal'
var url = store.state.url;



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
        plantingBatch:"",
        findFarmlandRegionId:""
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
             productId: [
            { validator: checkProductId, trigger: 'blur' }
          ],
               buyerId: [
            { validator: checkBuyerId, trigger: 'blur' }
          ],
            reviewerId: [
            { validator: checkReviewerId, trigger: 'blur' }
          ],
         
           },
      companyList:[],
      crops:[],
      batch:[],
      companyOptions: [],
    };
  },
  created() {
    this.getFarmlandRegionId();
    this.getBatch();
    this.getCrops();
    this.getRealName();
    this.getCompanyInfo();
    this.getCheckUser();
    this.getCompanyList();
    this.getCompanyName();
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

    getCheckUser() {
      getCheckUser().then(response => {
        const data = response.data
        this.checkUsers = data;    
        }
      );
    },

    getCompanyInfo(){
      getCompanyInfo().then(response =>{
        const data = response.data
        this.tempOrder.sellerId = data;
      })
    },

    getCompanyName(){
      getCompanyName().then(response =>{
        const data = response.data
        this.tempOrder.sellerName = data;
      })
    },

    getRealName() {
      getRealName().then(response => {
        const data = response.data
          this.tempOrder.orderCreator= data.sysUser.nickName
        }
      );
    },

    back(){
     this.$router.push({path:'/'+url+'/sale/myInvoice'});
    },


  getCompanyList() {
      getCompanyList().then(response => {
        const data = response.data
           this.companyList = data; 
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


    create(formName) {
      this.tempOrder.productBatch = this.tempOrder.findFarmlandRegionId +"-"+ this.tempOrder.plantingBatch;
      this.tempOrder.productTotal=this.tempOrder.productAmount * this.tempOrder.productPrice;
        this.$refs[formName].validate((valid) => {
            if (valid) {
              
                add(
                  this.tempOrder
                ).then(response => {
                  const data = response.data
                    alert(data)
                  }
                );
              
      this.$router.push({path:'/'+url+'/order/myOrder'});

          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },


  }
};
</script>

