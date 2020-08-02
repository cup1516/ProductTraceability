<template>
  <div>
    <el-card style="margin: 18px 2%;width: 95%">
      <el-table :data="tableData"
                stripe
                style="width: 100%">
        <el-table-column
          label="产品名"
          prop="productname"
          fit>
        </el-table-column>
        <el-table-column
          label="防伪码示例"
          prop="img"
          fit>
          <template width="40" slot-scope="scope">
            <el-image :src="scope.row.img" style="width: 100px; height: 100px"
                      :fit="fill"></el-image>
          </template>
        </el-table-column>
        <el-table-column
          label="企业名"
          prop="companyname"
          fit>
        </el-table-column>
        <el-table-column
          label="添加日期"
          prop="add_time"
          fit>
        </el-table-column>
        <el-table-column
          label="统一社会信用码"
          prop="code"
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
  </div>
</template>

<script>
    export default {
      name: "CodeManagement",
      data () {
        return {
          inputcode: '',
          pageSize: '6',
          total: '100',
          tableData: [{
            productname: '苹果',
            img: 'https://s1.ax1x.com/2020/07/07/UEpJoj.jpg',
            companyname: '苹果公司',
            add_time: '2020年4月26日',
            code: 'asddasdad'
          }, {
            productname: '香菜',
            img: 'https://s1.ax1x.com/2020/07/07/UEpdS0.jpg',
            companyname: '蔬菜公司',
            add_time: '2020年4月29日',
            code: 'asddzcx123'
          }, {
            productname: '菠萝',
            img: 'https://s1.ax1x.com/2020/07/07/UEpyTJ.jpg',
            companyname: '绿叶水果',
            add_time: '2020年4月29日',
            code: 'fruit0123'
          }]
        }
      },
      created () {
        const _this = this
        this.$axios.get('http://localhost:8443/consumer/findAll/0/5').then(function (resp) {
          // console.log(resp)
          _this.tableData = resp.data.content
          _this.pageSize = resp.data.size
          _this.total = resp.data.totalElements
        })
      },
      methods: {
        submitCode () {
          this.$axios.get('http://localhost:8443/code/findCode/' + this.inputcode).then(function (resp) {
            // console.log(resp)
            alert(resp.data)
            window.location.reload()
          })
          // alert(this.$axios.get('http://localhost:8181/code/findCode/' + this.inputcode))
        },
        handleCurrentChange (page) {
          var _this = this
          this.$axios.get('/article/' + this.pageSize + '/' + page).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.articles = resp.data.result.content
              _this.total = resp.data.result.totalElements
            }
          })
        },
        deleteConsumer (row) {
          this.$confirm('此操作将删除消费者信息，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              const _this = this
              this.$axios.delete('http://localhost:8443/consumer/deleteByPhone/' + row.phone).then(function (resp) {
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
        editConsumer (item) {
          this.$refs.edit.dialogFormVisible = true
          this.$refs.edit.form = {
            name: item.name,
            phone: item.phone,
            query_time: item.query_time,
            query_code: item.query_code,
            product: item.product,
            product_company: item.product_company,
            points: item.points
          }
        },
        page (currentPage) {
          const _this = this
          this.$axios.get('http://localhost:8443/consumer/findAll/' + (currentPage - 1) + '/5').then(function (resp) {
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
