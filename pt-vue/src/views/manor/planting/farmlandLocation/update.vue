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

        <el-button  size="mini" @click="update" type="primary" >提 交</el-button>
      </div>
    </el-card>
     
  </div>
</template>
<script>

import { update ,getParams} from "@/api/manor/planting/farmlandLocation";
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
      id:{
        id:""
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
   this.getParams();
  },
  methods: {

     getParams(){
      this.id.id=this.$route.query.id
       getParams(
          this.id
      ).then(response =>{
        const data = response.data
         this.tempFarmlandLocation.farmlandLocationId=data.farmlandLocationId;
           this.tempFarmlandLocation.farmlandLocationName=data.farmlandLocationName;
           this.tempFarmlandLocation.farmlandArea=data.farmlandArea;
           this.tempFarmlandLocation.farmlandPrincipal=data.farmlandPrincipal;
           this.tempFarmlandLocation.precipitation=data.precipitation;
           this.tempFarmlandLocation.climate=data.climate;
           this.tempFarmlandLocation.farmlandExplain=data.farmlandExplain;
      })
    },

 


      update() {
        var url = store.state.url;
        update(
          this.tempFarmlandLocation
        ).then(response =>{
        alert("修改成功！")
        this.$router.push({path:'/'+url+'/Management/planting/farmlandLocation'});
        })
    },

  }
};

</script>

