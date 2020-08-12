<template>
  <div>
  <el-button type="success" @click="dialogFormVisible = true" style="margin: 18px 2%">添加企业</el-button>

  <el-dialog title="添加企业" :visible.sync="dialogFormVisible">
    <el-form :model="addForm">
      <el-form-item prop="name" label="企业名称" :label-width="formLabelWidth">
        <el-input v-model="addForm.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="person" label="法人代表" :label-width="formLabelWidth">
        <el-input v-model="addForm.person" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="code" label="统一社会信用码" :label-width="formLabelWidth">
        <el-input v-model="addForm.code" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="number" label="工商注册号" :label-width="formLabelWidth">
        <el-input v-model="addForm.number" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="address" label="企业地址" :label-width="formLabelWidth">
        <el-input v-model="addForm.address" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitCompanyInfo">确 定</el-button>
    </div>
  </el-dialog>
  <el-card style="margin: 18px 2%;width: 95%">
    <el-table :data="tableData"
              stripe
              style="width: 100%">
      <el-table-column
        label="企业名称"
        prop="name"
        fit>
      </el-table-column>
      <el-table-column
        label="法人代表"
        prop="person"
        fit>
      </el-table-column>
      <el-table-column
        label="统一社会信用码"
        prop="code"
        fit>
      </el-table-column>
      <el-table-column
        label="工商注册号"
        prop="number"
        fit>
      </el-table-column>
      <el-table-column
        label="地址"
        prop="address"
        fit>
      </el-table-column>
      <el-table-column
        label="审核状态"
        prop="validity"
        width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.validity"
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
            @click.native.prevent="deleteCompany(scope.row)"
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
  <el-input v-model="inputCode" placeholder="请输入企业名或统一社会信用码" style="width: 300px"></el-input>
  <el-button type="primary" icon="el-icon-search" size="medium" @click="submitCode" >查询企业</el-button>
</div>
</template>

<script>
    export default {
      name: "CompanyManagement",
      data () {
        return {
          dialogTableVisible: false,
          inputCode: '',
          pageSize: '',
          total: '5',
          dialogFormVisible: false,
          addForm: {
            name: '',
            person: '',
            code: '',
            number: '',
            address: ''
          },
          formLabelWidth: '120px',
          tableData: [{
            name: '京东',
            person: '刘强东',
            code: '123',
            number: '456',
            address: '北京',
            validity: '0'
          }, {
            name: '淘宝',
            person: '马云',
            code: '123',
            number: '456',
            address: '杭州',
            validity: '0'
          }, {
            name: '腾讯',
            person: '马化腾',
            code: '123',
            number: '456',
            address: '深圳',
            validity: '0'
          }, {
            name: '苹果公司',
            person: '库克',
            code: '444',
            number: '04',
            address: '上海',
            validity: '0'
          }]
        }
      },
      created () {
        const _this = this
        this.$axios.get('/center/company/findAll/0/5').then(function (resp) {
          console.log(resp.content)
          _this.tableData = resp.content
          _this.pageSize = resp.size
          _this.total = resp.totalElements
          // eslint-disable-next-line eqeqeq
          if (resp.data.content.validity == '0') {
            _this.tableData.enabled = false
          } else _this.tableData.enabled = true
        })
      },
      methods: {
        submitCompanyInfo () {
          var _this = this
          this.$axios.post('/center/company/save', this.addForm).then(function (resp) {
            //console.log(resp.data)
            if (resp == "success") {//eslint-disable-line
              // console.log(resp.data)
              _this.$alert('新建成功', '提示', {
                confirmButtonText: '确定'
              })
              //_this.$router.replace('/admin/content/banner')
            } else {
              this.$alert(resp.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
        },
        commitStatusChange (value, row) {
          var _this = this
          this.$axios.post('/center/companyAccount/update', {
            name: row.name,
            code: row.code,
            validity: row.validity
          }).then(resp => {
            // eslint-disable-next-line eqeqeq
            if (resp == 'success') {
              _this.$alert('修改成功', '提示', {
                confirmButtonText: '确定'
              })
              //_this.$router.replace('/admin/content/banner')
            } else {
              this.$alert(resp.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          });
          if(value == '1') {
            this.$axios.post('/company_id/company_id/insert/' + '1234').then(function (resp2) {
              console.log(resp2)
              if (resp2 == "success!") {//eslint-disable-line
                // console.log(resp.data)
                _this.$alert('修改成功！', '提示', {
                  confirmButtonText: '确定'
                })
                // _this.$router.replace('/login')
              } else {
                this.$alert(resp2.message, '提示', {
                  confirmButtonText: '确定'
                })
              }
            });
          }
        },
        addCompany () {
          this.dialogTableVisible = true
        },
        submitCode () {
          this.$axios.get('/center/company/findOne/' + this.inputCode).then(function (resp) {
            // console.log(resp)
            alert(resp)
          })
          // alert(this.$axios.get('http://localhost:8181/code/findCode/' + this.inputcode))
        },
        deleteCompany (row) {
          this.$confirm('此操作将永久删除该条信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              const _this = this
              this.$axios.delete('/center/company/deleteById/' + row.id).then(function (resp) {
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
        page (currentPage) {
          const _this = this
          this.$axios.get('/center/company/findAll/' + (currentPage - 1) + '/5').then(function (resp) {
            // console.log(resp)
            _this.tableData = resp.content
            _this.pageSize = resp.size
            _this.total = resp.totalElements
          })
        },
        deleteBook (id) {
          this.$confirm('此操作将删除该企业信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              /* this.$axios
                .post('/admin/content/books/delete', {id: id}).then(resp => {
                if (resp && resp.data.code === 200) {
                  this.loadBooks()
                }
              }) */
            }
          ).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
        },
        editCompany (item) {
          /* this.$refs.edit.dialogFormVisible = true
          this.$refs.edit.form = {
            id: item.id,
            name: item.name,
            person: item.person,
            code: item.code,
            number: item.number
          } */
          this.$confirm('确定审核通过?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              this.$message({
                type: 'info',
                message: '已通过审核'
              })
              /* this.$axios.post('/admin/content/books/delete', {id: item.id}).then(resp => {
                if (resp && resp.data.code === 200) {
                  this.loadBooks()
                }
              }) */
            }
          ).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })
        }
      }
    }
</script>

<style scoped>

</style>
