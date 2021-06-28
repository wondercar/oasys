<template>
    <div id="uesrtext">
        <textarea placeholder="按下Enter键发送"
                  v-model="content"
                  v-on:keyup="addMessage"></textarea>
    </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'uesrtext',
    data () {
        return {
            content: ''
        }
    },
    computed: mapState([
        'currentSession'
    ]),
    methods: {
        addMessage (e) {
            // 按住enter键发送信息
            if (e.keyCode === 13 && this.content.length) {
                // 当前会话对象非空判断
                if (this.currentSession == null) {
                    this.$notify.error({
                        title: '错误',
                        message: '未选择信息接收对象',
                        showClose: false,
                        duration: 2000
                    });
                    // 消息栏信息置空
                    this.content = '';
                    return;
                }
                // 构造消息对象
                let msg = new Object();
                // 当前选中会话对象为消息接收方
                msg.to = this.currentSession.username;
                msg.content = this.content;
                // 发送信息
                this.$store.state.stomp.send("/ws/chat", {}, JSON.stringify(msg));
                // 调用发送信息方法
                this.$store.commit('addMessage', msg);
                // 消息栏信息置空
                this.content = '';
            }
        }
    }
}
</script>

<style lang="scss" scoped>
#uesrtext {
    position: absolute;
    bottom: 0;
    right: 0;
    width: 100%;
    height: 30%;
    border-top: solid 1px #ddd;
    > textarea {
        padding: 10px;
        width: 100%;
        height: 100%;
        border: none;
        outline: none;
    }
}
</style>
