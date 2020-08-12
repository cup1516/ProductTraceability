<template>
  <div>
    <el-dialog title="编辑消费者信息" :visible.sync="addFormVisible" :close-on-click-modal="true">
      <el-form :model="addForm">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="addForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="addForm.phone" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="查询时间" :label-width="formLabelWidth">
          <el-input v-model="addForm.query_time" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="查询地点" :label-width="formLabelWidth">
          <el-input v-model="addForm.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="防伪码" :label-width="formLabelWidth">
          <el-input v-model="addForm.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="查询产品" :label-width="formLabelWidth">
          <el-input v-model="addForm.product" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品公司" :label-width="formLabelWidth">
          <el-input v-model="addForm.product_company" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="积分" :label-width="formLabelWidth">
          <el-input v-model="addForm.points" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="addSubmit" >提 交</el-button>
      </div>
    </el-dialog>

    <el-card style="margin: 18px 2%;width: 95%">
      <el-table :data="tableData"
                stripe
                style="width: 100%">
        <el-table-column
          label="姓名"
          prop="name"
          fit>
        </el-table-column>
        <el-table-column
          label="手机号"
          prop="phone"
          fit>
        </el-table-column>
        <el-table-column
          label="查询时间"
          prop="query_time"
          fit>
        </el-table-column>
        <el-table-column
          label="查询地点"
          prop="address"
          fit>
        </el-table-column>
        <el-table-column
          label="防伪码"
          prop="code"
          fit>
        </el-table-column>
        <el-table-column
          label="查询产品"
          prop="product"
          fit>
        </el-table-column>
        <el-table-column
          label="产品公司"
          prop="product_company"
          fit>
        </el-table-column>
        <el-table-column
          label="积分"
          prop="points"
          fit>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="editConsumer(scope.row)"
              type="text"
              size="small">
              编辑
            </el-button>
            <el-button
              @click="deleteConsumer(scope.row)"
              type="text"
              size="small">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        @current-change="page">
      </el-pagination>
    </el-card>
    <el-input v-model="inputcode" placeholder="请输入防伪码" style="width: 300px"></el-input>
    <el-button type="primary" icon="el-icon-search" size="medium" @click="submitCode" >查询</el-button>
  </div>
</template>

<script>
    export default {
      name: "ConsumerManagement",
      data () {
        return {
          inputcode: '',
          pageSize: '6',
          total: '100',
          addFormVisible: false,
          formLabelWidth: '120px',
          addForm: {
            name: '',
            phone: '',
            query_time: '',
            address: '',
            code: '',
            product: '',
            product_company: '',
            points: ''
          },
          tableData: [{
            name: '张三',
            phone: '123456',
            query_time: '2020年4月26日',
            address: '北京',
            code: 'asddasdad',
            product: '苹果',
            product_company: 'APPLE',
            points: '0'
          }, {
            name: '李四',
            phone: '22222',
            query_time: '2020年4月26日',
            address: '上海',
            code: 'avbxcawd',
            product: '香蕉',
            product_company: '香蕉生产',
            points: '0'
          }, {
            name: 'Li Ming',
            phone: '333',
            query_time: '2020年4月26日',
            address: '天津',
            code: 'QWEGSADC',
            product: '玻璃',
            product_company: 'APPLE',
            points: '0'
          }]
        }
      },
      created () {
        const _this = this
        //this.$axios.get('/portal/blog/findAllDesc/0/5').then(resp => {
        this.$axios.get('/center/consumer/findAll/0/5').then(function (resp) {
          //console.log(resp)
          _this.tableData = resp.content
          _this.pageSize = resp.size
          _this.total = resp.totalElements
        })
      },
      methods: {
        addSubmit () {
          var _this = this
          this.$axios.post('/center/consumer/update', this.addForm).then(function (resp) {
            if (resp == "success") {//eslint-disable-line
              // console.log(resp.data)
              _this.$alert('修改成功', '提示', {
                confirmButtonText: '确定'
              })
              _this.$router.replace('/center/ConsumerManagement')
            } else {
              this.$alert(resp.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
        },
        submitCode () {
          this.$axios.get('/center/code/findCode/' + this.inputcode).then(function (resp) {
            // console.log(resp)
            alert(resp)
            //window.location.reload()
          })
          // alert(this.$axios.get('http://localhost:8181/code/findCode/' + this.inputcode))
        },
        deleteConsumer (row) {
          this.$confirm('此操作将删除消费者信息，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              const _this = this
              this.$axios.delete('http://localhost:9017/consumer/deleteByPhone/' + row.phone).then(function (resp) {
                _this.$alert('《' + row.name + '》删除成功', '消息', {
                  confirmButtonText: '确定',
                  callback: action => {
                    window.location.reload()
                  }
                })
              })
            }
          ).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
        },
        editConsumer (row) {
          this.addFormVisible = true
          this.addForm = Object.assign({}, row)
        },
        page (currentPage) {
          const _this = this
          this.$axios.get('http://localhost:9017/consumer/findAll/' + (currentPage - 1) + '/5').then(function (resp) {
            // console.log(resp)
            _this.tableData = resp.content
            _this.pageSize = resp.size
            _this.total = resp.totalElements
          })
        }
      }
    }
</script>

<style scoped>

</style>
