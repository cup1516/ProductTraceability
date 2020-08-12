<template>
  <div>
    <el-button type="success" @click="addCode" style="margin: 18px 2%">添加防伪码</el-button>
    <el-dialog title="添加防伪码" :visible.sync="addFormVisible" :close-on-click-modal="true">
      <el-form :model="addForm">
        <el-form-item label="产品名" :label-width="formLabelWidth">
          <el-input v-model="addForm.product" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="企业名" :label-width="formLabelWidth">
          <el-input v-model="addForm.company" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="查询次数" :label-width="formLabelWidth">
          <el-input v-model="addForm.times" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="防伪码" :label-width="formLabelWidth">
          <el-input v-model="addForm.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="防伪码图片" :label-width="formLabelWidth">
          <el-input v-model="addForm.image" autocomplete="off" placeholder="图片 URL"></el-input>
          <el-upload
            class="upload-demo"
            action="/center/centerCode/image"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="handleSuccess"
            :before-remove="beforeRemove"
            multiple
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible=false">取 消</el-button>
        <el-button type="primary" @click="submitCodeInfo" >提 交</el-button>
      </div>
    </el-dialog>

    <el-card style="margin: 18px 2%;width: 95%">
      <el-table :data="tableData"
                stripe
                style="width: 100%">
        <el-table-column
          label="产品名"
          prop="product"
          fit>
        </el-table-column>
        <el-table-column
          label="企业名"
          prop="company"
          fit>
        </el-table-column>
        <el-table-column
          label="查询次数"
          prop="times"
          fit>
        </el-table-column>
        <el-table-column
          label="防伪码"
          prop="code"
          fit>
        </el-table-column>
        <el-table-column
          label="防伪码示例"
          prop="image"
          fit>
          <template width="40" slot-scope="scope">
            <el-image :src="scope.row.image" style="width: 100px; height: 100px"
                      :fit="fill"></el-image>
          </template>
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
              @click="deleteCode(scope.row)"
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
          fileList: [{name: '', url: ''}],
          inputcode: '',
          pageSize: '',
          total: '5',
          addFormVisible: false,
          formLabelWidth: '120px',
          addForm: {
            product: '',
            company: '',
            times: '0',
            code: '',
            image: ''
          },
          tableData: [{
            product: '苹果',
            company: '苹果公司',
            times: '0',
            code: 'asddasdad',
            image: 'https://s1.ax1x.com/2020/07/07/UEpJoj.jpg'
          }, {
            product: '香菜',
            company: '蔬菜公司',
            times: '1',
            code: 'asddzcx123',
            image: 'https://s1.ax1x.com/2020/07/07/UEpdS0.jpg'
          }, {
            product: '菠萝',
            company: '绿叶水果',
            times: '3',
            code: 'fruit0123',
            image: 'https://s1.ax1x.com/2020/07/07/UEpyTJ.jpg'
          }]
        }
      },
      created () {
        const _this = this
        this.$axios.get('/center/centerCode/findAll/0/5').then(function (resp) {
          //console.log(resp)
          _this.tableData = resp.content
          _this.pageSize = resp.size
          _this.total = resp.totalElements
        })
      },
      methods: {
        addCode () {
          this.addFormVisible = true
        },
        submitCode () {
          this.$axios.get('http://localhost:8443/code/findCode/' + this.inputcode).then(function (resp) {
            // console.log(resp)
            alert(resp.data)
            window.location.reload()
          })
          // alert(this.$axios.get('http://localhost:8181/code/findCode/' + this.inputcode))
        },
        submitCodeInfo () {
          var _this = this
          this.$axios.post('/center/centerCode/save', this.addForm).then(function (resp) {
            //console.log(resp)
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
        handleCurrentChange (page) {
          var _this = this
          this.$axios.get('/article/' + this.pageSize + '/' + page).then(resp => {
            if (resp && resp.data.code === 200) {
              _this.articles = resp.data.result.content
              _this.total = resp.data.result.totalElements
            }
          })
        },
        page (currentPage) {
          const _this = this
          this.$axios.get('/center/centerCode/findAll/' + (currentPage - 1) + '/5').then(function (resp) {
            // console.log(resp)
            _this.tableData = resp.content
            _this.pageSize = resp.size
            _this.total = resp.data.totalElements
          })
        },
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        },
        handleExceed(files, fileList) {
          this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
          return this.$confirm(`确定移除 ${ file.name }？`);
        },
        handleSuccess (response) {
          this.addForm.image = response
          //console.log('test')
          //console.log(response)
          this.$emit('onUpload')
          this.$message.warning('上传成功')
        }
      }
    }
</script>

<style scoped>

</style>
