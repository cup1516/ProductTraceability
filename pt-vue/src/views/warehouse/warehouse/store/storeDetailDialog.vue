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
                    <el-input type="text" v-model="warehouseDetails.id"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='id' label="货架id号:">
                  <el-col :span="8">
                    <el-input type="text" v-model="warehouseDetails.name"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='warehouseName' label="所属仓库:">
                  <el-col :span="8">
                    <el-input type="text" v-model="warehouseDetails.address"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="8">
                <el-form-item prop='shelvesName' label="仓库备注:">
                  <el-col span="8">
                    <el-input type="text" v-model="warehouseDetails.remark"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="8">
                <el-form-item prop='createTime' label="创建时间:">
                  <el-col span="8">
                    <el-input type="text" v-model="warehouseDetails.createTime"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="8">
                <el-form-item prop='createTime' label="创建用户:">
                  <el-col span="8">
                    <el-input type="text" v-model="warehouseDetails.creator"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="8">
                <el-form-item prop='updateTime' label="更新时间:">
                  <el-col span="8">
                    <el-input type="text" v-model="warehouseDetails.updateTime"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>

              <el-col span="8">
                <el-form-item prop='updateTime' label="更新用户:">
                  <el-col span="8">
                    <el-input type="text" v-model="warehouseDetails.updator"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='updateTime' label="责任用户">
                  <el-col span="8">
                    <el-input type="text" v-model="warehouseDetails.principalName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="12">
                <el-form-item prop='updateTime' label="全部货位">
                  <el-col span="12">
                    <el-input type="text" v-model="allLocators"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item prop='updateTime' label="已满货位">
                  <el-col span="12">
                    <el-input type="text" v-model="fullLocators"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col span="12">
                <el-form-item prop='updateTime' label="可用货位">
                  <el-col span="12">
                    <el-input type="text" v-model="useLocators"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item prop='updateTime' label="全部货架">
                  <el-col span="12">
                    <el-input type="text" v-model="allShelves"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col span="12">
                <el-form-item prop='updateTime' label="库存详情">
                  <el-col span="12">
                    <el-input type="text" v-model="goodsAndNum"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
            </el-row>


          </el-form>

        </div>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script>
  import {getWarehouseById,getEmptyLocatorsByWarehouseId,getUseLocatorsByWarehouseId,getStorageByWarehouseId,getAllLocatorsByWarehouseId,getAllShelvesNamesByWarehouseId} from "../../api/store";

  export default {
    name: "storeDetailDialog",
    data(){
      return{
        warehouseDetails:'',
        allLocators:'',
        fullLocators:'',
        useLocators:'',
        goodsAndNum:'',
        allShelves:'',
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
      getWarehouseById(id).then((res)=>{
        this.warehouseDetails = res
        console.log(this.warehouseDetails)
      })
      getEmptyLocatorsByWarehouseId(id).then((res)=>{
        this.emptyLocators = res
        console.log(this.emptyLocators)
      })
      getUseLocatorsByWarehouseId(id).then((res)=>{
        this.useLocators = res
        console.log(this.useLocators)
      })
      getAllLocatorsByWarehouseId(id).then((res)=>{
        this.allLocators = res
        console.log(this.allLocators)
      })
      getAllShelvesNamesByWarehouseId(id).then((res)=>{
        this.allShelves = res

      })
      //库存
      getStorageByWarehouseId(id).then((res)=>{
        this.goodsAndNum = res
        console.log(this.goodsAndNum)
      })

    }
  }
</script>

<style scoped>

</style>
