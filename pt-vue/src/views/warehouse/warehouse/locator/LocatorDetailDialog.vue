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
                  <el-input type="text" v-model="locatorDetail.locatorCode"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位id号:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorId"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorName' label="货位名称:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.locatorName"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col span="8">
              <el-form-item prop='length' label="货位长度:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.length"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位宽度:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.width"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorName' label="货位高度:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.height"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col span="8">
              <el-form-item prop='length' label="货位创建:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.creator"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorCode' label="货位更新:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.updator"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='locatorName' label="货位载重:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.height"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>


          <el-row>

            <el-col span="8">
              <el-form-item prop='locatorName' label="创建时间:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.createTime"></el-input>
                </el-col>
              </el-form-item>
            </el-col>

            <el-col span="8">
              <el-form-item prop='locatorName' label="更新时间:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.updateTime"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>
          <h2>存放商品信息</h2>
          <el-row>
            <el-col span="8">
              <el-form-item prop='goodsName' label="商品名称:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.goodName"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='goodsNo' label="商品编码:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.goodNo"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col span="8">
              <el-form-item prop='price' label="商品单价:">
                <el-col :span="8">
                  <el-input type="text" v-model="locatorDetail.goodPrice"></el-input>
                </el-col>
              </el-form-item>
            </el-col>
          </el-row>


          <el-row>
            <el-col span="8">
              <el-form-item prop='goodsNum' label="商品数量:">
                <el-col span="8">
                  <el-input type="text" v-model="locatorDetail.goodNum"></el-input>
                </el-col>
              </el-form-item>
            </el-col>

          </el-row>
          <h2>所属仓库及货架</h2>
            <el-row>
              <el-col span="8">
                <el-form-item prop='warehouse' label="所属仓库:">
                  <el-col span="8">
                    <el-input type="text" v-model="locatorDetail.warehouseName"></el-input>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col span="8">
                <el-form-item prop='locatorCode' label="所属货架:">
                  <el-col :span="8">
                    <el-input type="text" v-model="locatorDetail.shelfName"></el-input>
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
      })

    }

  }
</script>

<style scoped>

</style>
