<template>
  <div class="app-container">
    
    <el-card class="box-card">
      <div align="center">
     <el-form
        class="small-space"
        label-position="left"
        label-width="200px"
        content="multipart/form-data"
        :model="tempFarmlandLocation"
        style="width: 600px; margin-left:50px;"
      >

      <el-form-item label="区域名:">
          <el-input type="text" v-model="tempFarmlandLocation.farmlandLocationName"></el-input>
        </el-form-item>
        <el-form-item label="负责人:">
          <el-input type="text" v-model="tempFarmlandLocation.farmlandPrincipal"></el-input>
        </el-form-item>
        <el-form-item label="区域大小(亩):">
            <el-slider v-model="tempFarmlandLocation.farmlandArea"   show-input :max="5000" :min="0" :step="1"></el-slider>
        </el-form-item>

          <el-form-item label="年降水量(ml):">
            <el-slider v-model="tempFarmlandLocation.precipitation"   show-input :max="4000" :min="0" :step="1"></el-slider>
        </el-form-item>
        
        <el-form-item label="气候类型:">
         <el-select placeholder="请选择"  style="width:400px" v-model="tempFarmlandLocation.climate">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
       
        <el-form-item label="描述信息">
          <el-input type="textarea" v-model="tempFarmlandLocation.farmlandExplain"></el-input>
        </el-form-item>

      </el-form>

        <el-button   size="mini" @click="create" type="primary" >创 建</el-button>
      </div>
    </el-card>
     
  </div>
</template>
<script>

import { create } from "@/api/manor/planting/farmlandLocation";
import store from '../../../../store/modules/portal'

export default {

  data() {
    return {
      formLabelWidth: '120px',
        tempFarmlandLocation:{
        farmlandLocationId:"",
        farmlandLocationName:"",
        farmlandPrincipal:"",
        farmlandArea:"",
        farmlandExplain:"",
        climate:"",
        precipitation:""
      },
      farmlandLocation:[],
      farmlandRegionTypes:[],
      options: [{
          value: '热带季风气候',
          label: '热带季风气候'
        }, {
          value: '亚热带季风气候',
          label: '亚热带季风气候'
        }, {
          value: '温带季风气候',
          label: '温带季风气候'
        }, {
          value: '温带大陆性气候',
          label: '温带大陆性气候'
        }, {
          value: '高原山地气候',
          label: '高原山地气候'
        }],
        value: ''
    };
  },
  created() {

   
  },
  methods: {

 
      

 

      create() {
          var url = store.state.url;
        create(
          this.tempFarmlandLocation
        ).then(response =>{
        alert("保存成功！"),
        this.$router.push({path:'/'+url+'/Management/planting/farmlandLocation'});
        })
    },
  }
};

</script>

