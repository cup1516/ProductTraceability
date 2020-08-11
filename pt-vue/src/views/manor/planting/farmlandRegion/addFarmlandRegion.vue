<template>
  <div class="app-container">
    
    <el-card class="box-card">
      <div align="center">
     <el-form
        class="small-space"
        :model="tempFarmlandRegion"
        label-position="left"
        label-width="200px"
        content="multipart/form-data"
        style="width: 800px; margin-left:50px;"
      >

      <el-row>
        <el-col :span="12"><div class="grid-content bg-purple">
          
        <el-form-item label="编号:"   label-width="100px" >
          <el-input type="text" v-model="tempFarmlandRegion.farmlandRegionId" style="width:200px"></el-input>
        </el-form-item>
          </div></el-col>
        <el-col :span="12"><div class="grid-content bg-purple-light">
        <el-form-item label="区域名称:"  label-width="100px"   >
          <el-select style="width:200px" v-model="tempFarmlandRegion.farmlandLocationName" placeholder="请选择" value-key="farmlandLocationName">
            <el-option  v-for="item in farmlandLocation" :key="item.farmlandLocationName" :label="item.farmlandLocationName" :value="item.farmlandLocationName"></el-option>
          </el-select>
        </el-form-item>
          </div></el-col>
      </el-row>

      <el-row>
        <el-col :span="12"><div class="grid-content bg-purple">
        <el-form-item label="负责人:"  label-width="100px" >
          <el-input type="text" v-model="tempFarmlandRegion.farmlandRegionPrincipal" style="width:200px"></el-input>
        </el-form-item>
          </div></el-col>
        <el-col :span="12"><div class="grid-content bg-purple-light">
        <el-form-item label="田地类型:" label-width="100px">
          <el-select style="width:200px" v-model="tempFarmlandRegion.farmlandRegionType" placeholder="请选择" value-key="name">
            <el-option  v-for="item in farmlandRegionTypes" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
          </div></el-col>
      </el-row>

        <el-form-item label="容重(g/cm3):" >
          <el-slider v-model="tempFarmlandRegion.bulkDensity"   show-input :max="2" :min="0" :step="0.01"></el-slider>
        </el-form-item>

        <el-form-item label="总孔隙度(%):" >
          <el-slider v-model="tempFarmlandRegion.porosity"    show-input :max="50" :min="0" :step="0.1"></el-slider>
        </el-form-item>

        <el-form-item label="PH值:" >
          <el-slider v-model="tempFarmlandRegion.ph"   show-input :max="14" :min="0" :step="0.1"></el-slider>
        </el-form-item>
        
        <el-form-item label="含盐量(%):">
          <el-slider v-model="tempFarmlandRegion.salt"   show-input :max="0.3" :min="0" :step="0.01"></el-slider>
        </el-form-item>

        <el-form-item label="有机质含量（g/kg）:" >
            <el-slider v-model="tempFarmlandRegion.organic"   show-input :max="100" :min="0" :step="0.1"></el-slider>
        </el-form-item>
        <el-form-item label="含氮量（g/kg）:" >
            <el-slider v-model="tempFarmlandRegion.n"   show-input :max="10" :min="0" :step="0.1"></el-slider>
        </el-form-item>
        <el-form-item label="含磷量（g/kg）:" >
          <el-slider v-model="tempFarmlandRegion.p"   show-input :max="5" :min="0" :step="0.1"></el-slider>
        </el-form-item>
        <el-form-item label="含钾量（g/kg）:" >
          <el-slider v-model="tempFarmlandRegion.k"   show-input :max="100" :min="0" :step="0.1"></el-slider>
        </el-form-item>

        <el-form-item label="土壤渗透系数（cm/s）:" >
          <el-slider v-model="tempFarmlandRegion.soilPermeabilityCoefficient"   show-input :max="30" :min="0" :step="0.1"></el-slider>
        </el-form-item>

        <el-form-item label="区块大小(亩):" >
           <el-slider v-model="tempFarmlandRegion.farmlandRegionArea"   show-input :max="10" :min="0" :step="0.1"></el-slider>
        </el-form-item>

      </el-form>
        <el-button  size="mini" @click="back">取 消</el-button>
        <el-button size="mini"  @click="create" type="primary" >创 建</el-button>
      </div>
    </el-card>
     
  </div>
</template>
<script>

import { create,getAllFarmlandLocation,getFarmlandRegionType } from "@/api/manor/planting/farmlandRegion";
import store from '../../../../store/modules/portal'

export default {

  data() {
    return {
      formLabelWidth: '120px',
       tempFarmlandRegion:{
        farmlandRegionId:"",
        farmlandRegionPrincipal:"",
        farmlandRegionArea:"",
        farmlandRegionExplain:"",
        farmlandRegionType:"",
        bulkDensity:"",
        porosity:"",
        ph:"",
        salt:"",
        organic:"",
        n:"",
        p:"",
        k:"",
        soilPermeabilityCoefficient:"",
        farmlandLocationName:"",
      },
      farmlandLocation:[],
      farmlandRegionTypes:[],
    };
  },
  created() {
    this.getAllFarmlandLocation();
    this.getFarmlandRegionType();
   
  },
  methods: {

    getFarmlandRegionType(){
      getFarmlandRegionType().then(response =>{
        const data = response.data
        this.farmlandRegionTypes = data;
      })
    },

    getAllFarmlandLocation(){
      getAllFarmlandLocation().then(response =>{
        const data = response.data
        this.farmlandLocation = data;
      })
    },


      back() {
        var url = store.state.url;

        this.$router.push({path:'/'+url+'/planting/farmlandRegion'});
      },


    create() {
        var url = store.state.url;

      create(
        this.tempFarmlandRegion
      ).then(response =>{
        alert("保存成功！");
        this.$router.push({path:'/'+url+'/planting/farmlandRegion'});

      })
    },

  
  }
};

</script>

