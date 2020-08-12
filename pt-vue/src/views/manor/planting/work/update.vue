<template>
  <div class="app-container">
    
    <el-card class="box-card">
      <div align="center">
     <el-form
        class="small-space"
        :model="tempWork"
        label-position="right"
        label-width="150px"
        content="multipart/form-data"
        style="width: 500px; margin-left:50px;"
      >

        <el-form-item label="田地编号：">
        <el-select  v-model="tempWork.findFarmlandRegionId" placeholder="请选择地块"  style="width:300px">
            <el-option  v-for="item in farmlandRegion" :key="item.farmlandRegionId" :label="item.farmlandRegionId" :value="item.farmlandRegionId"></el-option>
          </el-select>
       </el-form-item>


      <el-form-item label="种植批次">
        <el-select  v-model="tempWork.batch" placeholder="请选择种植批次"  style="width:300px">
            <el-option  v-for="item in batch" :key="item.id" :label="item.id" :value="item.id"></el-option>
          </el-select>
       </el-form-item>
        <el-form-item label="作业类型:" prop="work">
        <el-select  v-model="tempWork.work" placeholder="请选择作业类型"  style="width:300px">
            <el-option  v-for="item in workTypes" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>

 
        <el-form-item label="采摘数量（单位/kg）" v-if="(dialogStatus!= 'find') &&(tempWork.work == '采摘') "  >
          <!-- <el-input type="text" v-model="tempWork.amount"   style="width:300px"></el-input> -->
           <el-input-number v-model="tempWork.amount"  :min="1" :max="100000"  style="width:300px"></el-input-number>
        </el-form-item>

        <el-form-item label="物料名称" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')" >
          <el-input type="text" v-model="tempWork.materialName"  style="width:300px"></el-input>
        </el-form-item>

        <el-form-item label="物料公司" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')"  >
          <el-input type="text" v-model="tempWork.materialCompany"  style="width:300px"></el-input>
        </el-form-item>

        <el-form-item label="物料描述" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')" >
          <el-input type="textarea" v-model="tempWork.materialInformation"  style="width:300px"></el-input>
        </el-form-item>

        <el-form-item label="农作物种类">
        <el-select  v-model="tempWork.crops" placeholder="请选择农作物种类"  style="width:300px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
       </el-form-item>

        <el-form-item label="记录图片" prop="ImgUrl" >
          <el-input v-model="tempWork.picture"  style="width:300px"></el-input>
          <img-upload @onUpload="uploadImg" ref="imgUpload"></img-upload>
        </el-form-item>
        
        <el-form-item label="责任人">
        <el-input type="text" v-model="tempWork.staff" :disabled="true"  style="width:300px"></el-input>
        </el-form-item>
        
        <el-form-item label="备注信息"   >
          <el-input type="text" v-model="tempWork.remark"  style="width:300px"></el-input>
        </el-form-item> 

        <el-form-item label="审批人:"  >
          <el-select  v-model="tempWork.checkUser" placeholder="请选择审批人"  style="width:300px">
            <el-option  v-for="item in checkUsers" :key="item.userName" :label="item.userName" :value="item.userName"></el-option>
          </el-select>
        </el-form-item>      

      </el-form>
     

        <el-button 
         @click="update" 
         icon="el-icon-check"
         type="success"  
          size="mini"
        >提交</el-button>

        
      </div>
    </el-card>
     
  </div>
</template>
<script>

import ImgUpload from "../imgUpload";
import { getParams } from "@/api/manor/planting/work/detail";
import { update } from "@/api/manor/planting/work/update";
import {getCheckUser,getUserName,getMaterial,getBatch,getCrops,getWorkTypes,getFarmlandRegionId,getUserRealName} from "@/api/manor/planting/work/add";

import store from '../../../../store/modules/portal';
var url = store.state.url;


export default {
    components: {ImgUpload},
  data() {
    return {
      tempFind:{},
      formLabelWidth: '120px',
      fileList: [],
      url: '',
      batch:[],
      tempWork:{
        id:"",
        work:"",
        crops:"",
        picture:"",
        video:"",
        remark:"",
        staff:"",
        amount:"",
        date:"",
        findFarmlandRegionId:"",
        stime:"",
        etime:"",
        batch:"",
        materialName:"",
        materialInformation:"",
        materialCompany:"",
        checkUser:'',
        checkNumber:'',
        updateDate:''
      },
      workTypes:[],
      id:{
        id:""
      },
      crops:[],
      checkUsers:[]
    };
  },
  created() {
    this.getFarmlandRegionId();
    this.getBatch();
    this.getCrops();
    this.getParams();
    this.getCheckUser();
    this.getWorkTypes();

  },
  methods: {

   
   
     getParams() {
       this.id.id=this.$route.query.id
        getParams(
          this.id
        ).then(response => {
        const data = response.data
        this.tempWork.id=data.id
        this.tempWork.checkNumber=data.checkNumber
        this.tempWork.date=data.date
        this.tempWork.checkUser=data.checkUser
        this.tempWork.crops=data.crops
        this.tempWork.findFarmlandRegionId = data.findFarmlandRegionId;
        this.tempWork.batch = data.batch;
        this.tempWork.work=data.work,
        this.tempWork.picture=data.picture,
        this.tempWork.remark=data.remark;
        this.tempWork.staff=data.staff;
        this.tempWork.amount = data.amount;
        this.tempWork.materialCompany = data.materialCompany;
        this.tempWork.materialName = data.materialName;
        this.tempWork.materialInformation = data.materialInformation;
        this.tempWork.date = data.date;
        }
      );
    },
 
      back() {
        this.$router.push({path:'/'+url+'/planting/myWork'});
      },

      
    getCheckUser() {
      getCheckUser().then(response => {
        const data = response.data
        this.checkUsers = data;
        }
      );
    },


    getUserName() {
      getUserName().then(response => {
        const data = response.data
         this.tempWork.staff= data.sysUser.userName
        }
      );
    },

      getMaterial() {
      getMaterial().then(response => {
        const data = response.data
        this.materials = data;
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

   

    getCrops() {
      getCrops().then(response => {
        const data = response.data
      this.crops = data;
        }
      );
    },

      getWorkTypes() {
      getWorkTypes().then(response => {
        const data = response.data
        this.workTypes = data;
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
    uploadImg () {
        this.tempWork.picture = this.$refs.imgUpload.url
        console.log(this.tempWork.picture+"aaaaaa")
      },
    

    update() {
      update(
        this.tempWork
      ).then(response=>{
        alert("修改成功");
        
        this.getParams();
      })
    },
  }
};

</script>

