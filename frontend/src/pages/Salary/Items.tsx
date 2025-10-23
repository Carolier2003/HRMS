import React, { useState, useRef } from 'react';
import { ProTable, ProColumns } from '@ant-design/pro-components';
import { Button, Modal, Form, Input, InputNumber, Select, message, Popconfirm } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { useAccess } from '@umijs/max';
import { getSalaryItems, createSalaryItem, updateSalaryItem, deleteSalaryItem } from '@/services/api';
import type { API } from '@/services/typings';

const SalaryItems: React.FC = () => {
  const access = useAccess();
  const [modalVisible, setModalVisible] = useState(false);
  const [editingItem, setEditingItem] = useState<API.SalaryItem | null>(null);
  const [form] = Form.useForm();
  const actionRef = useRef<any>();

  const columns: ProColumns<API.SalaryItem>[] = [
    {
      title: '项目名称',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: '项目类型',
      dataIndex: 'type',
      width: 120,
      valueEnum: {
        '基本工资': { text: '基本工资', status: 'Success' },
        '奖金': { text: '奖金', status: 'Processing' },
        '扣款': { text: '扣款', status: 'Error' },
        '其他': { text: '其他', status: 'Default' },
      },
    },
    {
      title: '默认金额',
      dataIndex: 'defaultAmount',
      width: 120,
      render: (text) => `¥${text || 0}`,
    },
    {
      title: '是否启用',
      dataIndex: 'enabled',
      width: 100,
      valueEnum: {
        true: { text: '启用', status: 'Success' },
        false: { text: '禁用', status: 'Default' },
      },
    },
    {
      title: '描述',
      dataIndex: 'description',
      width: 200,
      render: (text) => text || '-',
    },
    {
      title: '操作',
      width: 150,
      fixed: 'right',
      hideInSearch: true,
      render: (_, record) => (
        access.canManageSalary && (
          <div style={{ display: 'flex', gap: '8px' }}>
            <Button
              type="link"
              size="small"
              icon={<EditOutlined />}
              onClick={() => {
                setEditingItem(record);
                form.setFieldsValue(record);
                setModalVisible(true);
              }}
            >
              编辑
            </Button>
            <Popconfirm
              title="确认删除"
              description="确定要删除这个工资项目吗？"
              onConfirm={async () => {
                try {
                  await deleteSalaryItem(record.id!);
                  message.success('删除成功');
                  actionRef.current?.reload();
                } catch (error) {
                  message.error('删除失败');
                }
              }}
              okText="确认"
              cancelText="取消"
            >
              <Button
                type="link"
                size="small"
                danger
                icon={<DeleteOutlined />}
              >
                删除
              </Button>
            </Popconfirm>
          </div>
        )
      ),
    },
  ];

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      if (editingItem) {
        await updateSalaryItem(editingItem.id!, values);
        message.success('更新成功');
      } else {
        await createSalaryItem(values);
        message.success('创建成功');
      }
      setModalVisible(false);
      setEditingItem(null);
      form.resetFields();
      actionRef.current?.reload();
    } catch (error) {
      message.error(editingItem ? '更新失败' : '创建失败');
    }
  };

  const handleCancel = () => {
    setModalVisible(false);
    setEditingItem(null);
    form.resetFields();
  };

  return (
    <>
      <ProTable<API.SalaryItem>
        columns={columns}
        actionRef={actionRef}
        request={async (params) => {
          try {
            const response = await getSalaryItems();
            if (response.code === 200) {
              return {
                data: response.data,
                success: true,
                total: response.data.length,
              };
            }
            return {
              data: [],
              success: false,
            };
          } catch (error) {
            console.error('加载工资项目失败:', error);
            return {
              data: [],
              success: false,
            };
          }
        }}
        rowKey="id"
        search={false}
        pagination={{
          pageSize: 10,
        }}
        toolBarRender={() => [
          access.canManageSalary && (
            <Button
              type="primary"
              key="primary"
              icon={<PlusOutlined />}
              onClick={() => {
                setEditingItem(null);
                form.resetFields();
                setModalVisible(true);
              }}
            >
              新增工资项目
            </Button>
          ),
        ]}
      />

      <Modal
        title={editingItem ? '编辑工资项目' : '新增工资项目'}
        open={modalVisible}
        onOk={handleSubmit}
        onCancel={handleCancel}
        width={600}
      >
        <Form
          form={form}
          layout="vertical"
          initialValues={{
            enabled: true,
            type: '基本工资',
          }}
        >
          <Form.Item
            name="name"
            label="项目名称"
            rules={[{ required: true, message: '请输入项目名称' }]}
          >
            <Input placeholder="请输入项目名称" />
          </Form.Item>

          <Form.Item
            name="type"
            label="项目类型"
            rules={[{ required: true, message: '请选择项目类型' }]}
          >
            <Select placeholder="请选择项目类型">
              <Select.Option value="基本工资">基本工资</Select.Option>
              <Select.Option value="奖金">奖金</Select.Option>
              <Select.Option value="扣款">扣款</Select.Option>
              <Select.Option value="其他">其他</Select.Option>
            </Select>
          </Form.Item>

          <Form.Item
            name="defaultAmount"
            label="默认金额"
            rules={[{ required: true, message: '请输入默认金额' }]}
          >
            <InputNumber
              placeholder="请输入默认金额"
              style={{ width: '100%' }}
              min={0}
              precision={2}
              formatter={(value) => `¥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
              parser={(value) => value!.replace(/¥\s?|(,*)/g, '')}
            />
          </Form.Item>

          <Form.Item
            name="enabled"
            label="是否启用"
            rules={[{ required: true, message: '请选择是否启用' }]}
          >
            <Select placeholder="请选择是否启用">
              <Select.Option value={true}>启用</Select.Option>
              <Select.Option value={false}>禁用</Select.Option>
            </Select>
          </Form.Item>

          <Form.Item
            name="description"
            label="描述"
          >
            <Input.TextArea
              placeholder="请输入项目描述"
              rows={3}
            />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default SalaryItems;
