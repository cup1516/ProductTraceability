<template>
  <div class="app-container">
    
    <el-card class="box-card">
      <div align="center">
     <el-form
        class="small-space"
        :model="tempWork"
        label-position="right"
        label-width="150px"
        style="width: 500px; margin-left:50px;"
        :rules="rules"
        ref="tempWork"
      >

        <el-form-item label="田地编号:" prop="findFarmlandRegionId">
        <el-select  v-model="tempWork.findFarmlandRegionId"  placeholder="请选择地块"  style="width:300px">
            <el-option  v-for="item in farmlandRegion" :key="item.farmlandRegionId" :label="item.farmlandRegionId" :value="item.farmlandRegionId"></el-option>
          </el-select>
       </el-form-item>

      <el-form-item label="种植批次:" prop="batch">
        <el-select  v-model="tempWork.batch" autocomplete="off"  placeholder="请选择种植批次"  style="width:300px">
            <el-option  v-for="item in batch" :key="item.id" :label="item.id" :value="item.id"></el-option>
          </el-select>
       </el-form-item>

        <el-form-item label="作业类型:" prop="work">
        <el-select  v-model="tempWork.work" placeholder="请选择作业类型"  style="width:300px">
            <el-option  v-for="item in workTypes" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="采摘数量（单位/kg）:" v-if="(dialogStatus!= 'find') &&(tempWork.work == '采摘') "  >   
          <el-input-number v-model="tempWork.amount"  style="width:300px" :min="1" :max="1000000" ></el-input-number>
        </el-form-item>

        <el-form-item label="物料名称:" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')" >
          <el-select  v-model="tempWork.materialName" placeholder="请选择作业类型"  style="width:300px">
            <el-option  v-for="item in materials" :key="item.materialName" :label="item.materialName" :value="item.materialName"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="农作物种类:" prop="crops">
        <el-select  v-model="tempWork.crops" placeholder="请选择农作物种类"  style="width:300px">
            <el-option  v-for="item in crops" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
       </el-form-item>

        <el-form-item label="记录图片:" prop="ImgUrl"  >
          <el-input v-model="tempWork.picture"  style="width:300px"></el-input>
          <img-upload @onUpload="uploadImg" ref="imgUpload"></img-upload>
        </el-form-item>
        
        <!-- <el-form-item label="责任人:">
          <el-input type="text" v-model="tempWork.staff" :disabled="true"  style="width:300px"></el-input>
        </el-form-item> -->
        
        <el-form-item label="备注信息:"  prop="remark" >
          <el-input type="textarea" v-model="tempWork.remark"  style="width:300px"></el-input>
        </el-form-item>   

        
        <el-form-item label="审批人:"  prop="checkUser">
          <el-select  v-model="tempWork.checkUser" placeholder="请选择审批人"  style="width:300px">
            <el-option  v-for="item in checkUsers" :key="item.userName" :label="item.userName" :value="item.userName"></el-option>
          </el-select>
        </el-form-item>


      </el-form>
        <el-button  @click="create('tempWork')" type="primary" size="mini" >创 建</el-button>
      </div>
    </el-card>
     
  </div>
</template>
<script>

import ImgUpload from "../imgUpload";
import store from '../../../../store/modules/portal';

import {create,getCheckUser,getUserName,getMaterial,getBatch,getCrops,getWorkTypes,getFarmlandRegionId,getUserRealName} from "@/api/manor/planting/work/add";

 var url = store.state.url;

export default {
  components: {ImgUpload},

  data() {

      var checkFindFarmlandRegionId = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("田地不能为空"))
        }
        callback();
      }

      var checkBatch = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("批次不能为空"))
        }
        callback();
      }

      var checkWork = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("作业类型不能为空"))
        }
        callback();
      }

     var checkCrops = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("农作物种类不能为空"))
        }
        callback();
      }

     var checkPicture = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("图片不能为空"))
        }
        callback();
      }

         var checkCheckUser = (rule,value,callback)=>{
        if(!value){
          return callback(new Error("审批人不能为空"))
        }
        callback();
      }

    
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
        checkUser:"",
        checkNumber:""
      },
      crops:[],
      workTypes:[],
      materials:[],
      checkUsers:[],

      rules: {
          findFarmlandRegionId: [
            { validator: checkFindFarmlandRegionId, trigger: 'blur' }
          ],
          batch: [
            { validator: checkBatch, trigger: 'blur' },
          ],    
          work: [
            { validator:checkWork, trigger: 'blur' },
          ],   
            crops: [
            { validator: checkCrops, trigger: 'blur' },
          ],  
            checkUser: [
            { validator: checkCheckUser, trigger: 'blur' },
          ],  
 
        
        }
    };
  },
  created() {
    this.getFarmlandRegionId();
    this.getBatch();
    this.getCrops();
    this.getUserName();
    this.getWorkTypes();
    this.getMaterial();
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

    getUserName() {
      getUserName().then(response => {
        const data = response.data
         this.tempWork.staff= data.userDto.userName
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
    

    create(formName) {
        this.$refs[formName].validate((valid) => {
            if (valid) {

                 create(
                 this.tempWork
                ).then(response => {
                  const data = response.data
                    alert("保存成功！");
                this.$router.push({path:'/'+url+'/Management/planting/myWork'});
                  }
                );
            
          } else {
            console.log('error submit!!');
            return false;
          }
        });
    },

  
  }
};

</script>

