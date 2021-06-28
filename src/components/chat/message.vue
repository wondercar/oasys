<template>
    <div id="message"
         v-scroll-bottom="sessions">
        <!-- 判断currentSession是否存在，存在才展示当前消息 -->
        <ul v-if="currentSession">
            <!-- 遍历消息 -->
            <li v-for="entry in sessions[user.username+'#'+currentSession.username]">
                <p class="time">
                    <!-- 时间过滤器 -->
                    <span>{{entry.date | time}}</span>
                </p>

                <div class="main"
                     :class="{self:entry.notSelf}">
                    <img class="avatar"
                         :src="entry.notSelf ? user.userFace : currentSession.userFace"
                         alt="">
                    <p class="text">{{entry.content}}</p>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'message',
    data () {
        return {
            // 默认加载用户图片数据URL
            // img: 'https://portrait.gitee.com/uploads/avatars/user/2617/7853024_mornd_1608529482.png!avatar200',
            user: JSON.parse(window.sessionStorage.getItem('user'))
        }
    },
    computed: mapState([
        // 动态更新聊天数据
        'sessions',
        'currentSession'
    ]),
    filters: {
        // 过滤器：格式化聊天时间戳
        time (date) {
            if (date) {
                date = new Date(date);
            }
            // 分钟数小于10单独进行字符拼接
            var minute = date.getMinutes().length == 1 ? ("0" + date.getMinutes()).toString() : date.getMinutes().toString();
            // 判断
            return `${date.getFullYear()}-${date.getMonth()}-${date.getDate()} ${date.getHours()}:${minute}`;
        }
    },
    directives: {
        // 这个是vue的自定义指令
        // 发送消息后滚动到底部
        'scroll-bottom' (el) {
            //console.log(el.scrollTop);
            setTimeout(function () {
                el.scrollTop += 9999;
            }, 1)
        }
    }
}
</script>

<style lang="scss" scoped>
#message {
    padding: 15px;
    max-height: 68%;
    overflow-y: scroll;
    ul {
        padding-left: 0;
        list-style-type: none;
        li {
            margin-bottom: 15px;
        }
    }
    .time {
        text-align: center;
        margin: 7px 0;
        > span {
            display: inline-block;
            padding: 0 18px;
            font-size: 12px;
            color: #fff;
            background-color: #dcdcdc;
            border-radius: 2px;
        }
    }
    .main {
        .avatar {
            float: left;
            margin: 0 10px 0 0;
            border-radius: 3px;
            width: 30px;
            height: 30px;
        }
        .text {
            display: inline-block;
            padding: 0 10px;
            max-width: 80%;
            background-color: #fafafa;
            border-radius: 4px;
            line-height: 30px;
        }
    }
    .self {
        text-align: right;
        .avatar {
            float: right;
            margin: 0 0 0 10px;
            border-radius: 3px;
            width: 30px;
            height: 30px;
        }
        .text {
            display: inline-block;
            padding: 0 10px;
            max-width: 80%;
            background-color: #b2e281;
            border-radius: 4px;
            line-height: 30px;
        }
    }
}
</style>
