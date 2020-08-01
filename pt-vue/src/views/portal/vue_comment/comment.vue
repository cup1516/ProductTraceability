]<template>
    <b_comment
		ref="my_comment"
		:placeholder="placeholder"
		:if_not_logined="if_not_logined"
		:emoji_list="emoji_list"
		:verify_once="verify_once"
		@submit_click="submit_click"
	/>
</template>

<script>
import b_comment from './b_comment.vue'
export default {
  name:'comment',
	components: {
		'b_comment':b_comment,
	},
  data() {
    return {
		placeholder: "请提出意见与建议",//默认文字提示。
    if_not_logined: true,//用户是否没有登录。
    // 颜文字列表。
		emoji_list: ['(⌒▽⌒)', '（￣▽￣）', '(=・ω・=)', '(｀・ω・´)', '(〜￣△￣)〜', '(･∀･)', '(￣3￣)', '╮(￣▽￣)╭', '( ´_ゝ｀)', '←_←', '→_→', '(<_<)', '(>_>)', '(;¬_¬)', '("▔□▔)/', '(ﾟДﾟ≡ﾟдﾟ)!?', 'Σ(ﾟдﾟ;)', '(｡･ω･｡)', '(´･_･`)', '（￣へ￣）', '(╯°口°)╯(┴—┴', '_(:3」∠)_'],
		verify_once: false,//未登录时，每次评论都需输入验证码。
    commentname: "sss",
    commenttext:"aaa",
    };
  },
  computed:{
	  comment_text(){//获取子组件的评论内容。
		  return this.$refs.my_comment.insert_comment.comment_text;
	  },
	  comment_name(){//获取子组件的评论昵称。
		  return this.$refs.my_comment.insert_comment.comment_name;
	  }
  },
  methods:{
	  //点击评论按钮后，触发的事件。
	  //（在这之前会先检验是否为空、是否输入验证码）
	  submit_click(){
      console.log(this.$store.getters.company_id)
      this.$confirm('是否保存并发布评论?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          this.$axios
            .post('/portal/comment/addOrUpdate',{
                commentName: this.comment_name,
                commentText: this.comment_text,
                companyId :  this.$store.getters.company_id
              }
            ).then(resp => {
            console.log(resp);
              this.$message({
                type: 'info',
                message: '已保存成功'
              })
          })
        }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消发布'
        })
      })


	  }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
