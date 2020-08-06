<template>
  <div>
    <el-button type="success" @click="addOrder">添加订单</el-button>

    <el-dialog title="添加订单" :visible.sync="addFormVisible" :close-on-click-modal="true">
      <el-form :model="addForm">
        <el-form-item label="订单编号" :label-width="formLabelWidth">
          <el-input v-model="addForm.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="企业名称" :label-width="formLabelWidth">
          <el-input v-model="addForm.companyname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用码" :label-width="formLabelWidth">
          <el-input v-model="addForm.companycode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品" :label-width="formLabelWidth">
          <el-input v-model="addForm.product" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input v-model="addForm.quantity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单价" :label-width="formLabelWidth">
          <el-input v-model="addForm.singleprice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="总价" :label-width="formLabelWidth">
          <el-input v-model="addForm.totalprice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="更改时间" :label-width="formLabelWidth">
          <el-input v-model="addForm.create_time" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="审核人员" :label-width="formLabelWidth">
          <el-input v-model="addForm.auditor" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="addSubmit" >提 交</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑订单" :visible.sync="editFormVisible" :close-on-click-modal="true">
      <el-form :model="editForm">
        <el-form-item label="订单编号" :label-width="formLabelWidth">
          <el-input v-model="editForm.number" autocomplete="off" disabled='disabled'></el-input>
        </el-form-item>
        <el-form-item label="企业名称" :label-width="formLabelWidth">
          <el-input v-model="editForm.company_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="统一社会信用码" :label-width="formLabelWidth">
          <el-input v-model="editForm.company_code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="产品" :label-width="formLabelWidth">
          <el-input v-model="editForm.product" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input v-model="editForm.quantity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单价" :label-width="formLabelWidth">
          <el-input v-model="editForm.singleprice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="总价" :label-width="formLabelWidth">
          <el-input v-model="editForm.totalprice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="更改时间" :label-width="formLabelWidth">
          <el-input v-model="editForm.create_time" autocomplete="off" disabled='disabled'></el-input>
        </el-form-item>
        <el-form-item label="审核人员" :label-width="formLabelWidth">
          <el-input v-model="editForm.auditor" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="editSubmit" >提 交</el-button>
      </div>
    </el-dialog>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table :data="tableData"
                stripe
                style="width: 100%">
        <el-table-column
          fixed
          label="订单编号"
          prop="number"
          width="150"
          fit>
        </el-table-column>
        <el-table-column
          label="企业名称"
          prop="company_name"
          width="140"
          fit>
        </el-table-column>
        <el-table-column
          label="统一社会信用码"
          prop="company_code"
          width="120"
          fit>
        </el-table-column>
        <el-table-column
          label="产品"
          prop="product"
          width="120"
          fit>
        </el-table-column>
        <el-table-column
          label="数量"
          prop="quantity"
          width="120"
          fit>
        </el-table-column>
        <el-table-column
          label="单价"
          prop="singleprice"
          width="120"
          fit>
        </el-table-column>
        <el-table-column
          label="总价"
          prop="totalprice"
          width="120"
          fit>
        </el-table-column>
        <el-table-column
          label="更改时间"
          prop="create_time"
          width="120"
          fit>
        </el-table-column>
        <el-table-column
          label="审核状态"
          prop="state"
          width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0"
              @change="(value) => commitStatusChange(value, scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="editOrder(scope.row)"
              type="text"
              size="small">
              编辑
            </el-button>
            <el-button
              @click.native.prevent="deleteOrder(scope.row)"
              type="text"
              size="small">
              移除
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="审核人员"
          prop="auditor"
          width='120'
          fit>
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
  </div>
</template>

<script>
    export default {
      name: "index",
      data () {
        return {
          dialogTableVisible: false,
          inputCode: '',
          pageSize: '',
          total: '5',
          dialogFormVisible: false,
          addForm: {
            number: '',
            company_name: '',
            company_code: '',
            product: '',
            quantity: '',
            singleprice: '',
            totalprice: '',
            create_time: '',
            auditor: ''
          },
          editForm: {
            number: '',
            companyname: '',
            companycode: '',
            product: '',
            quantity: '',
            singleprice: '',
            totalprice: '',
            create_time: '',
            auditor: 'admin'
          },
          addFormVisible: false,
          editFormVisible: false,
          formLabelWidth: '120px',
          tableData: [{
            number: 1,
            company_name: '京东',
            company_code: '刘强东',
            product: '苹果',
            quantity: '10000',
            singleprice: '0.01',
            totalprice: '100',
            create_time: '2020年6月15日',
            state: '0',
            auditor: 'admin'
          }, {
            number: 2,
            company_name: '水果公司A',
            company_code: '张三',
            product: '香蕉',
            quantity: '20000',
            singleprice: '0.02',
            totalprice: '400',
            create_time: '2020年6月15日',
            state: '0',
            auditor: 'admin'
          }]
        }
      },
      created () {
        const _this = this
        this.$axios.get('http://localhost:9017/order/findAll/0/5').then(function (resp) {
          //console.log(resp.data.content)
          _this.tableData = resp.content
          _this.pageSize = resp.ize
          _this.total = resp.totalElements
          // eslint-disable-next-line eqeqeq
          if (resp.content.state == '0') {
            _this.tableData.enabled = false
          } else _this.tableData.enabled = true
        })
      },
      methods: {
        commitStatusChange (value, row) {
          var _this = this
          this.$axios.post('http://localhost:9017/order/updateState', {
            number: row.number,
            auditor: row.auditor,
            state: row.state
          }).then(resp => {
            // eslint-disable-next-line eqeqeq
            if (resp == 'success') {
              _this.$alert('修改成功', '提示', {
                confirmButtonText: '确定'
              })
              // _this.$router.replace('/admin/content/index')
            } else {
              this.$alert(resp.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
        },
        addSubmit () {
          var _this = this
          this.$axios.post('http://localhost:9017/order/save', this.addForm).then(function (resp) {
            if (resp == "success") {//eslint-disable-line
              // console.log(resp.data)
              _this.$alert('修改成功', '提示', {
                confirmButtonText: '确定'
              })
              //_this.$router.replace('/admin/content/index')
            } else {
              this.$alert(resp.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
        },
        editSubmit () {
          var _this = this
          this.$axios.post('http://localhost:9017/order/update', this.editForm).then(function (resp) {
            // console.log(resp.data)
            if (resp == "success") {//eslint-disable-line
              // console.log(resp.data)
              _this.$alert('修改成功', '提示', {
                confirmButtonText: '确定'
              })
              //_this.$router.replace('/admin/content/index')
            } else {
              this.$alert(resp.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
        },
        deleteOrder (row) {
          this.$confirm('此操作将永久删除该条信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              const _this = this
              this.$axios.delete('http://localhost:9017/order/deleteByNumber/' + row.number).then(function (resp) {
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
        addOrder () {
          this.addFormVisible = true
        },
        editOrder (row) {
          this.editFormVisible = true
          this.editForm = Object.assign({}, row)
        },
        page (currentPage) {
          const _this = this
          this.$axios.get('http://localhost:9017/company/findAll/' + (currentPage - 1) + '/5').then(function (resp) {
            // console.log(resp)
            _this.tableData = resp.data.content
            _this.pageSize = resp.data.size
            _this.total = resp.data.totalElements
          })
        }
      }
    }
</script>

<style scoped>

</style>
