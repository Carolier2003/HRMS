import React, { useState, useRef } from 'react';
import { ProTable, ProColumns } from '@ant-design/pro-components';
import { Button, Modal, Form, Input, Select, message, Popconfirm, Tag, Card, Row, Col } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, SafetyOutlined } from '@ant-design/icons';
import { getRoles, createRole, updateRole, deleteRole } from '@/services/api';
import type { API } from '@/services/typings';

const { Option } = Select;

const PermissionManagement: React.FC = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [editingRole, setEditingRole] = useState<API.Role | null>(null);
  const [form] = Form.useForm();
  const actionRef = useRef<any>();

  const columns: ProColumns<API.Role>[] = [
    {
      title: '角色名称',
      dataIndex: 'roleName',
      width: 150,
    },
    {
      title: '角色代码',
      dataIndex: 'roleCode',
      width: 120,
      render: (text) => (
        <Tag color={getRoleColor(text)}>
          {text}
        </Tag>
      ),
    },
    {
      title: '描述',
      dataIndex: 'description',
      width: 200,
      render: (text) => text || '-',
    },
    {
      title: '权限说明',
      dataIndex: 'roleCode',
      width: 300,
      render: (_, record) => (
        <div>
          {getPermissionDescription(record.roleCode).map((permission, index) => (
            <Tag key={index} color="blue" style={{ marginBottom: 4 }}>
              {permission}
            </Tag>
          ))}
        </div>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      width: 150,
      valueType: 'dateTime',
    },
    {
      title: '操作',
      width: 150,
      fixed: 'right',
      hideInSearch: true,
      render: (_, record) => (
        <div style={{ display: 'flex', gap: '8px' }}>
          <Button
            type="link"
            size="small"
            icon={<EditOutlined />}
            onClick={() => {
              setEditingRole(record);
              form.setFieldsValue(record);
              setModalVisible(true);
            }}
          >
            编辑
          </Button>
          <Popconfirm
            title="确认删除"
            description="确定要删除这个角色吗？"
            onConfirm={async () => {
              try {
                await deleteRole(record.id!);
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
      ),
    },
  ];

  const getRoleColor = (roleCode: string) => {
    switch (roleCode) {
      case 'ADMIN':
        return 'red';
      case 'HR':
        return 'blue';
      case 'EMPLOYEE':
        return 'green';
      default:
        return 'default';
    }
  };

  const getPermissionDescription = (roleCode: string) => {
    switch (roleCode) {
      case 'ADMIN':
        return ['系统管理', '用户管理', '权限管理', '员工管理', '工资管理', '统计报表'];
      case 'HR':
        return ['员工管理', '工资管理', '统计报表'];
      case 'EMPLOYEE':
        return ['查看个人信息', '查看个人工资条'];
      default:
        return ['基础权限'];
    }
  };

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      if (editingRole) {
        await updateRole(editingRole.id!, values);
        message.success('更新成功');
      } else {
        await createRole(values);
        message.success('创建成功');
      }
      setModalVisible(false);
      setEditingRole(null);
      form.resetFields();
      actionRef.current?.reload();
    } catch (error) {
      message.error(editingRole ? '更新失败' : '创建失败');
    }
  };

  const handleCancel = () => {
    setModalVisible(false);
    setEditingRole(null);
    form.resetFields();
  };

  return (
    <>
      {/* 权限说明卡片 */}
      <Row gutter={16} style={{ marginBottom: 16 }}>
        <Col span={8}>
          <Card size="small" title={<><SafetyOutlined /> 管理员 (ADMIN)</>}>
            <div>
              <Tag color="red">系统管理</Tag>
              <Tag color="red">用户管理</Tag>
              <Tag color="red">权限管理</Tag>
              <Tag color="red">员工管理</Tag>
              <Tag color="red">工资管理</Tag>
              <Tag color="red">统计报表</Tag>
            </div>
            <p style={{ marginTop: 8, fontSize: 12, color: '#666' }}>
              拥有系统所有权限，可以管理用户、角色、员工信息和工资数据
            </p>
          </Card>
        </Col>
        <Col span={8}>
          <Card size="small" title={<><SafetyOutlined /> HR专员 (HR)</>}>
            <div>
              <Tag color="blue">员工管理</Tag>
              <Tag color="blue">工资管理</Tag>
              <Tag color="blue">统计报表</Tag>
            </div>
            <p style={{ marginTop: 8, fontSize: 12, color: '#666' }}>
              负责员工信息管理和工资数据录入，可以查看统计报表
            </p>
          </Card>
        </Col>
        <Col span={8}>
          <Card size="small" title={<><SafetyOutlined /> 普通员工 (EMPLOYEE)</>}>
            <div>
              <Tag color="green">查看个人信息</Tag>
              <Tag color="green">查看个人工资条</Tag>
            </div>
            <p style={{ marginTop: 8, fontSize: 12, color: '#666' }}>
              只能查看和修改自己的个人信息，查看个人工资条
            </p>
          </Card>
        </Col>
      </Row>

      <ProTable<API.Role>
        columns={columns}
        actionRef={actionRef}
        request={async (params) => {
          try {
            const response = await getRoles();
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
            console.error('加载角色列表失败:', error);
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
          <Button
            type="primary"
            key="primary"
            icon={<PlusOutlined />}
            onClick={() => {
              setEditingRole(null);
              form.resetFields();
              setModalVisible(true);
            }}
          >
            新增角色
          </Button>,
        ]}
      />

      <Modal
        title={editingRole ? '编辑角色' : '新增角色'}
        open={modalVisible}
        onOk={handleSubmit}
        onCancel={handleCancel}
        width={600}
      >
        <Form
          form={form}
          layout="vertical"
        >
          <Form.Item
            name="roleName"
            label="角色名称"
            rules={[{ required: true, message: '请输入角色名称' }]}
          >
            <Input placeholder="请输入角色名称" />
          </Form.Item>

          <Form.Item
            name="roleCode"
            label="角色代码"
            rules={[{ required: true, message: '请输入角色代码' }]}
          >
            <Input placeholder="请输入角色代码（如：ADMIN、HR、EMPLOYEE）" />
          </Form.Item>

          <Form.Item
            name="description"
            label="描述"
          >
            <Input.TextArea
              placeholder="请输入角色描述"
              rows={3}
            />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default PermissionManagement;
