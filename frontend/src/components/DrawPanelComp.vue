<script setup>
import {onMounted} from "vue";
import {newInstance} from "@jsplumb/browser-ui"

onMounted(() => {

  const instance = newInstance({
    container: document.getElementById("diagram"),
    dragOptions: {
      containment: "parentEnclosed"
    }
  })

  instance.manage(document.getElementById("control2"))
  instance.manage(document.getElementById("control1"))

  console.log($("#diagram").html())
})
</script>

<template>
  <div class="row">
    <div class="col-md-3">
      <div id="toolbox">
        <div class="block-center">
          <button>Добавить место</button>
        </div>
        <div class="block-center">
          <label>Размеры</label>
          <input>
          <input>
        </div>
        <div class="block-center">
          <button>Сохранить</button>
        </div>
      </div>
    </div>
    <div class="col-md-9">
      <div id="diagram" style="width: 1000px; height: 600px; position: relative">
        <div id="control2" class="control" style="left: 180px; top: 42px; height: 100px;"
             data-jtk-managed="control2">Control2
        </div>
        <div id="control1" class="control" style="left: 68px; top: 36px; width: 50px; height: 150px;">Control1
        </div>
      </div>
    </div>
  </div>

<!-- Сохранение html в бд? -> сохранение атрибутов типа width, height, top, left, id + diagramID -->
<!-- jsPlumb -->

</template>

<style scoped>
#diagram, #toolbox {
  margin: 20px 0;
  border: 2px solid lightgray;
  height: 90vh;
}

#toolbox {
  display: flex;
  padding: 10px;
  flex-flow: row wrap;
  align-content: flex-start;
}

.control {
  border: green 2px solid;
  border-radius: 10px;
  width: 120px;
  text-align: center;
  cursor: move;
  height: fit-content;
  height: -moz-max-content;
  background-color: white;
}

#diagram .control {
  position: absolute;
}
</style>