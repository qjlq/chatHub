<template>
    <!-- 触发按钮 -->
    <el-button size="small" type="primary" @click="showModal = true" class="ListButton">
      {{'显示任务队列' }}
    </el-button>

    <!-- 弹窗 -->
    <el-dialog 
      v-model="showModal" 
      title="创建新群" 
      width="90vw"
      max-height="90vh"
      
    >
      <div class="task-list">
        <div class="task-header">
          <div v-for="(col, index) in columns" 
              :key="index" 
              :style="{ width: col.width || 'auto' }">
            {{ col.label }}
          </div>
        </div>
        
        <div v-for="(task, index) in this.tasks" 
            :key="task.id || index" 
            class="task-item">
          <div class="task-name" :style="{ width: this.columns[0].width || 'auto' }">
            {{ task.name }}
          </div>
          <div class="task-user" :style="{ width: this.columns[1].width || 'auto' }">
            {{ task.user }}
          </div>
          <div class="task-time" :style="{ width: this.columns[2].width || 'auto' }">
            {{ formatTime(task.time) }}
          </div>
          <div class="task-type" :style="{ width: this.columns[3].width || 'auto' }">
            <span :class="'type-' + task.type">{{ getTypeLabel(task.type) }}</span>
          </div>
          <div class="task-status" :style="{ width: this.columns[4].width || 'auto' }">
            <span :class="'type-' + task.taskStatus">{{ getTypeLabel(task.taskStatus) }}</span>
          </div>
          <div class="task-progress" :style="{ width: this.columns[5].width || 'auto' }">
            <div class="progress-container">
              <div class="progress-bar" 
                  :style="{ width: task.progress + '%' }"
                  :class="getProgressClass(task.progress)"></div>
            </div>
            <span class="progress-text">{{ task.progress }}%</span>
          </div>
        </div>
      </div>
    </el-dialog>

</template>

<script>
export default {
  name: 'TaskQueueModal',
  props: {
  },
  data() {
    return {
      tableLoading: false,
      showModal: false,
      tableKey: 0,
      progress:30,
      columns:  [
          { label: '任务名称', width: '25%' },
          { label: '提交用户', width: '15%' },
          { label: '提交时间', width: '20%' },
          { label: '任务类型', width: '15%' },
          { label: '任务状态', width: '15%' },
          { label: '完成进度', width: '25%' }
      ],
      typeLabels: {
        type: Object,
        default: () => ({
          upload: '文件上传',
          process: '数据处理',
          backup: '数据备份',
          other: '其他'
        })
      },
      tasks: [
        { 
          id: 1, 
          name: '用户数据导出', 
          user: '张三', 
          time: new Date('2023-05-15 09:30:00'), 
          type: 'process', 
          progress: 75 
        },
        { 
          id: 2, 
          name: '系统备份', 
          user: '李四', 
          time: '2023-05-15 10:15:00', 
          type: 'backup', 
          progress: 30 
        },
        { 
          id: 3, 
          name: '图片上传', 
          user: '王五', 
          time: new Date(), 
          type: 'upload', 
          progress: 100 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        },
        { 
          id: 1, 
          name: '用户数据导出', 
          user: '张三', 
          time: new Date('2023-05-15 09:30:00'), 
          type: 'process', 
          progress: 75 
        },
        { 
          id: 2, 
          name: '系统备份', 
          user: '李四', 
          time: '2023-05-15 10:15:00', 
          type: 'backup', 
          progress: 30 
        },
        { 
          id: 3, 
          name: '图片上传', 
          user: '王五', 
          time: new Date(), 
          type: 'upload', 
          progress: 100 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        },
        { 
          id: 1, 
          name: '用户数据导出', 
          user: '张三', 
          time: new Date('2023-05-15 09:30:00'), 
          type: 'process', 
          progress: 75 
        },
        { 
          id: 2, 
          name: '系统备份', 
          user: '李四', 
          time: '2023-05-15 10:15:00', 
          type: 'backup', 
          progress: 30 
        },
        { 
          id: 3, 
          name: '图片上传', 
          user: '王五', 
          time: new Date(), 
          type: 'upload', 
          progress: 100 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        },
        { 
          id: 4, 
          name: '日志清理', 
          user: '赵六', 
          time: new Date(), 
          type: 'other', 
          progress: 5 
        },
      ]
    }
  },
  methods: {
    openModal() {
      this.showModal = true
    },
    closeModal() {
      this.showModal = false
    },
    formatTime(time) {
      if (time instanceof Date) {
        return time.toLocaleString()
      }
      return time
    },
    getTypeLabel(type) {
      return this.typeLabels[type] || type
    },
    getProgressClass(progress) {
      if (progress >= 100) return 'progress-complete'
      if (progress >= 70) return 'progress-high'
      if (progress >= 30) return 'progress-medium'
      return 'progress-low'
    }
  }
}
</script>

<style scoped>
/* 按钮样式 */
.ListButton {
  margin: 0% 0% 0% 2%;
  background: linear-gradient(135deg, #3b82f6, #6366f1);
  border: none;
  border-radius: 8px;
  /* padding: 0% 0% 0% 3%; */
  /* padding: 8px 16px; */
  box-shadow: 0 2px 4px rgba(59, 130, 246, 0.2);
}
.show-modal-btn {
  padding: 8px 16px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
  margin-bottom: 9%;
  /* align-items:baseline */
}

.show-modal-btn:hover {
  background-color: #66b1ff;
}

/* 弹窗样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-container {
  background-color: white;
  border-radius: 8px;
  width: 80%;
  max-width: 900px;
  max-height: 80vh;
  display: flex;
  flex-direction: column;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.modal-header {
  padding: 16px 20px;
  border-bottom: 1px solid #ebeef5;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-header h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
}

.close-btn {
  font-size: 20px;
  color: #909399;
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
}

.close-btn:hover {
  color: #409eff;
}

.modal-content {
  padding: 20px;
  overflow-y: auto;
  flex: 1;
}

.modal-footer {
  padding: 12px 20px;
  border-top: 1px solid #ebeef5;
  display: flex;
  justify-content: flex-end;
}

.close-modal-btn {
  padding: 8px 16px;
  background-color: #f0f0f0;
  color: #606266;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.close-modal-btn:hover {
  background-color: #e0e0e0;
}

/* 任务列表样式 */
.task-list {
  display: flex;
  flex-direction: column;
  font-size: 14px;
  overflow-y:"scroll"
}

.task-header, .task-item {
  display: flex;
  padding: 12px 0;
  border-bottom: 1px solid #ebeef5;
  align-items: center;
}

.task-header {
  font-weight: bold;
  color: #909399;
  border-bottom: 2px solid #ebeef5;
}

.task-item:last-child {
  border-bottom: none;
}

.task-item:hover {
  background-color: #f5f7fa;
}

/* 进度条样式 */
.progress-container {
  width: 80%;
  height: 8px;
  background-color: #ebeef5;
  border-radius: 4px;
  display: inline-block;
  margin-right: 8px;
  vertical-align: middle;
}

.progress-bar {
  height: 100%;
  border-radius: 4px;
  transition: width 0.3s ease;
}

.progress-complete {
  background-color: #67c23a;
}

.progress-high {
  background-color: #409eff;
}

.progress-medium {
  background-color: #e6a23c;
}

.progress-low {
  background-color: #f56c6c;
}

.progress-text {
  color: #606266;
}

/* 任务类型标签样式 */
.task-type span {
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.type-upload {
  background-color: #f0f9eb;
  color: #67c23a;
}

.type-process {
  background-color: #ecf5ff;
  color: #409eff;
}

.type-backup {
  background-color: #fdf6ec;
  color: #e6a23c;
}

.type-other {
  background-color: #fef0f0;
  color: #f56c6c;
}
</style>