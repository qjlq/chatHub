<template lang="">
    <div class="window-show">

    </div>
    <div class = "window-input" >

    </div>
</template>

<script>
export default {
    data() {
        return {
            roomName:"test"
        }
    },
}
</script>

<style lang="">
    .window-input {
        position: relative;
        width: 100%;
        padding: 10px 20px 20px;
        box-sizing: border-box;
        flex-direction: column;
        border-top: var(--border-in-light);
        box-shadow: var(--card-shadow);
    }
    .window-show{
        flex: 1 1;
        overflow: auto;
        overflow-x: hidden;
        padding: 20px 20px 40px;
        position: relative;
        overscroll-behavior: none;
    }
</style>
