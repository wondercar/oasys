<template>
    <div id="list">
        <ul style="padding-left: 0">
            <li v-for="item in admins"
                :class="{ active: currentSession?item.username === currentSession.username:false }"
                v-on:click="changeCurrentSession(item)">
                <!--   :class="[item.id === currentSession ? 'active':'']" -->
                <img class="avatar"
                     :src="item.userFace">
                <!-- 当前用户+系统用户组成一个消息key -->
                <el-badge :is-dot="isDot[user.username+'#'+item.username]">
                    <p class="name">{{item.name}}</p>
                </el-badge>
            </li>
        </ul>
    </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
    name: 'list',
    data () {
        return {
            // 当前用户
            user: JSON.parse(window.sessionStorage.getItem('user'))
        }
    },
    computed: mapState([
        'isDot',
        'admins',
        'currentSession'
    ]),
    methods: {
        changeCurrentSession: function (currentSession) {
            this.$store.commit('changeCurrentSession', currentSession)
        }
    }
}
</script>

<style lang="scss" scoped>
#list {
    height: 480px;
    overflow: auto;
    li {
        list-style: none;
        padding: 15px 15px;
        border-bottom: 1px solid #292c33;
        cursor: pointer;
        &:hover {
            background-color: rgba(255, 255, 255, 0.03);
        }
    }
    li.active {
        background-color: rgba(255, 255, 255, 0.1);
    }
    .avatar {
        border-radius: 2px;
        width: 30px;
        height: 30px;
        vertical-align: middle;
    }
    .name {
        display: inline-block;
        margin-left: 15px;
        margin-top: 0px;
        margin-bottom: 5px;
        margin-right: 5px;
    }
}
</style>
