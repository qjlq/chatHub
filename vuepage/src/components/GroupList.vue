<template lang="">
    <div>
        <div v-if="tester.left" class="message-container">
            <div class="message-container">
                <div class="message-head">
                    <div class="message-image">
                        {{tester.cid}}
                    </div>
                </div>
                <div class="message-text">
                    <p style="font-size: 14px;" dir="auto">{{tester.msg}}</p>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    methods: {
        updatedGroup(){
            
        }
    },
}
</script>
<style lang="css">
    
</style>