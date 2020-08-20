<template>
  <div>
    <el-dialog
      :visible.sync="isVisible"
      :title="title"
      :close-on-click-modal='false'
      :close-on-press-escape='false'
      :modal-append-to-body="false"
      fullscreen
      @close="closeDialog">

      <div class="form">

        <h2> 货位信息</h2>
        <el-form ref="form" style="margin:10px;width:auto;">
          <el-row>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位编码:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.code"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位id号:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.id"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorName' label="货位名称:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.name"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col span="8">
              <el-form-item prop='length' label="货位长度:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.length"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位宽度:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.width"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorName' label="货位高度:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.height"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col span="8">
              <el-form-item prop='length' label="货位创建:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.creator"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位更新:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.updator"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorName' label="货位载重:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.height"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>


          <el-row>

            <el-col span="8">
              <el-form-item prop='locatorName' label="创建时间:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.createTime"></el-input>
                </el-col>
              </el-form-item>
            </el-col>

            <el-col span="8">
              <el-form-item prop='locatorName' label="更新时间:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorDTO.updateTime"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
          <h2>存放商品信息</h2>
          <el-row>
            <el-col span="8">
              <el-form-item prop='goodsName' label="商品名称:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.stockDTO.commodityName"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='goodsNo' label="商品编码:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.stockDTO.commodityBar"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>


          <el-row>
            <el-col span="8">
              <el-form-item prop='goodsNum' label="商品数量:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.stockDTO.cqty"></el-input>
                </el-col>
              </el-form-item>
            </el-col>

          </el-row>
          <h2>所属仓库及货架</h2>
            <el-row>
              <el-col span="8">
                <el-form-item prop='warehouse' label="所属仓库:">
                  <el-col span="8">
                    <el-input type="text" v-model="locatorDetail.locatorDTO.warehouseId"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='locatorCode' label="所属货架:">
                  <el-col :span="8">
                    <el-input type="text" v-model="locatorDetail.locatorDTO.shelvesName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              </el-row>

          <el-row style="text-align: center">
            <el-button style="width: 10%" type="primary" @click="isVisible = false">确   认</el-button>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>


<script>
  import { getLocatorDetail } from '../../api/locator/index.js'
  import {initOrderNo} from "../../api/allocation";
  export default {
    name: "LocatorDetailDialog",
    data () {
      return{
        isVisible: this.isShow,
        title:'',
        locatorDetail:''

      }
    },

    props: {
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
      getLocatorDetail(id).then((res)=>{
      this.locatorDetail = res
        console.log("uuuuuuuuuu"+res)
      })

    }

  }
</script>

<style scoped>

</style>
