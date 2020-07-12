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
        style="width: 900px; margin-left:50px;"
      >

     <el-row>
        <el-col :span="12"><div >
                <el-form-item label="作业编号:">
          <div v-text="tempWork.id" align="left"></div>
       </el-form-item>
          </div></el-col>
        <el-col :span="12"><div > 
        <el-form-item label="田地编号:">
          <div v-text="tempWork.findFarmlandRegionId" align="left"></div>
       </el-form-item>
          </div></el-col>
      </el-row>


      <el-row>
        <el-col :span="12"><div >
      
      <el-form-item label="种植批次:">
          <div v-text="tempWork.batch" align="left"></div>
       </el-form-item>
          </div></el-col>
        <el-col :span="12"><div >
                  <el-form-item label="作业类型:">
          <div v-text="tempWork.work" align="left"></div>
        </el-form-item>
          </div></el-col>
      </el-row>

      
      <el-row>
        <el-col :span="12"><div >
                  <el-form-item label="图片:">
          <template width="40" slot-scope="scope">
            <el-popover placement="right" title="" trigger="hover">
            <img :src="tempWork.picture" style="width: 350px; height: 250px" >
            <img slot="reference" :src="tempWork.picture" :alt="tempWork.picture" style="max-height: 100px;max-width: 200px">
            </el-popover>
          </template>
        </el-form-item>
          </div></el-col>
        <el-col :span="12"><div>

          </div></el-col>
      </el-row>


        <el-row>
          <el-col :span="12"><div >
                              <el-form-item label="采摘数量(单位/kg):" v-if="tempWork.work == '采摘'"  >
          <div v-text="tempWork.amount" align="left"></div>
        </el-form-item>
            </div></el-col>
          <el-col :span="12"><div >
                                <el-form-item label="物料名称:" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')" >
          <div v-text="tempWork.materialName" align="left"></div>
        </el-form-item>
            </div></el-col>
        </el-row>



        <el-row>
          <el-col :span="12"><div>
                  <el-form-item label="物料公司:" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')"  >
          <div v-text="tempWork.materialCompany" align="left"></div>
        </el-form-item>
            </div></el-col>
          <el-col :span="12"><div >
                   <el-form-item label="物料描述:" v-if="(tempWork.work == '施肥')||(tempWork.work == '栽苗')" >
          <div v-text="tempWork.materialInformation" align="left"></div>
        </el-form-item>
            </div></el-col>
        </el-row>


        <el-row>
          <el-col :span="12"><div >
                  <el-form-item label="农作物种类:">
          <div v-text="tempWork.crops" align="left"></div>
       </el-form-item>
            </div></el-col>
          <el-col :span="12"><div >
                     <el-form-item label="责任人:">
          <div v-text="tempWork.staff" align="left"></div>
        </el-form-item>
            </div></el-col>
        </el-row>

      <el-row>
          <el-col :span="12"><div>
                      <el-form-item label="备注信息:"   >
          <div v-text="tempWork.remark" align="left"></div>
        </el-form-item> 
            </div></el-col>
          <el-col :span="12"><div >
 
            </div></el-col>
        </el-row>
 

      </el-form>
        <el-button 
        size="mini" 
        type="primary"  
        @click="back">返回</el-button>

      </div>
    </el-card>
     
  </div>
</template>
<script>

import { getParams } from "@/api/manor/production/production";

export default {
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
        materialCompany:""
      },
      crops:[],
      id:{
        id:""
      }
    };
  },
  created() {
    this.getParams();
  },
  methods: {

    getParams(){
      this.id.id=this.$route.query.id
       getParams(
        this.id
      ).then(response => {
      const data = response.data
      this.tempWork.id=data.id
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
        })
    },

      back() {
        this.$router.push({path:'/production'});
      },
  }
};

</script>

