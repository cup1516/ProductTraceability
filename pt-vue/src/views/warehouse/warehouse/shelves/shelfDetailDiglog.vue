<template>
  <el-dialog
    :visible.sync="isVisible"
    :title="title"
    :close-on-click-modal='false'
    :close-on-press-escape='false'
    :modal-append-to-body="false"
    fullscreen
    @close="closeDialog">
    <el-row style="text-align: center">
      <el-col :span="8">
        <div  style="text-align: center" >
          <el-col>
            <span style="padding: 35px; font-size: 25px">货架信息</span>
          </el-col>
          <el-col>
            <img style="padding-left: 20px; width: 150px;height: 150px" src="../../assets/add.png">
          </el-col>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="form">
          <el-form ref="form" style="margin:10px;width:auto;">
            <el-row>
              <el-col span="8">
                <el-form-item prop='shelvesName' label="货架名称:">
                  <el-col span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.shelvesName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='id' label="货架id号:">
                  <el-col :span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.id"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='warehouseName' label="所属仓库:">
                  <el-col :span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.warehouseName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="8">
                <el-form-item prop='shelvesName' label="创建用户:">
                  <el-col span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.createUserName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="8">
                <el-form-item prop='createTime' label="创建时间:">
                  <el-col span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.createTime"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>


              <el-col span="8">
                <el-form-item prop='updateTime' label="更新时间:">
                  <el-col span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.updateTime"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>


            <el-row>
              <el-col span="8">
                <el-form-item prop='updator' label="更新用户:">
                  <el-col span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.updateUserName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="8">
                <el-form-item prop='companyId' label="公司编号:">
                  <el-col span="8">
                    <el-input type="text" v-model="shelfDetails.shelvesDTO.companyId"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>


            <el-row>
              <el-col span="16">
                <el-form-item prop='map' label="库存详情:">
                  <el-col span="11">
                    <el-input type="text" v-model="shelfDetails.map1"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="16">
                <el-form-item prop='map' label="拥有货位:">
                  <el-col span="11">
                    <el-input type="text" v-model="shelfDetails.map2"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

            </el-row>
            <el-row style="bottom:-160px;width:auto;text-align: center ">
              <el-button style="width: 10%" type="primary" @click="isVisible = false">确   认</el-button>
            </el-row>


          </el-form>

        </div>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script>
  import {getShelfDetail} from "../../api/shelves";

  export default {
    name: "shelfDetailDialog",

    data(){
      return{
        shelfDetails:'',
        isVisible: this.isShow,
        title:'',
      }
    },
    props:{
      isShow: Boolean,
      dialogRow: Object
    },
    methods:{
      closeDialog: function () {
        this.$emit('closeDialog')
      },
    },
    created() {
      let id =this.dialogRow.id
      getShelfDetail(id).then((res)=>{
        this.shelfDetails = res
        console.log(this.shelfDetails.map.toString()+"9999955")
      })

    }
  }
</script>

<style scoped>

</style>
