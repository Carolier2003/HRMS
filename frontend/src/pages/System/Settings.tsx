import React, { useState, useEffect } from 'react';
import { Card, Form, Input, InputNumber, Switch, Button, message, Divider, Row, Col, Typography } from 'antd';
import { SettingOutlined, SaveOutlined, ReloadOutlined } from '@ant-design/icons';
import { getSystemSettings, updateSystemSettings } from '@/services/api';
import type { API } from '@/services/typings';

const { Title, Text } = Typography;

const SystemSettings: React.FC = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [settings, setSettings] = useState<API.SystemSettings | null>(null);

  useEffect(() => {
    loadSettings();
  }, []);

  const loadSettings = async () => {
    try {
      setLoading(true);
      const response = await getSystemSettings();
      if (response.code === 200) {
        setSettings(response.data);
        form.setFieldsValue(response.data);
      }
    } catch (error) {
      message.error('加载系统设置失败');
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async () => {
    try {
      const values = await form.validateFields();
      setLoading(true);
      const response = await updateSystemSettings(values);
      if (response.code === 200) {
        message.success('保存成功');
        setSettings(values);
      } else {
        message.error(response.msg || '保存失败');
      }
    } catch (error) {
      message.error('保存失败');
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    if (settings) {
      form.setFieldsValue(settings);
      message.info('已重置为上次保存的设置');
    }
  };

  return (
    <div style={{ padding: 24 }}>
      <Title level={2}>
        <SettingOutlined /> 系统设置
      </Title>
      
      <Form
        form={form}
        layout="vertical"
        onFinish={handleSave}
      >
        {/* 系统基本信息 */}
        <Card title="系统基本信息" style={{ marginBottom: 16 }}>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="systemName"
                label="系统名称"
                rules={[{ required: true, message: '请输入系统名称' }]}
              >
                <Input placeholder="请输入系统名称" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="systemVersion"
                label="系统版本"
                rules={[{ required: true, message: '请输入系统版本' }]}
              >
                <Input placeholder="请输入系统版本" />
              </Form.Item>
            </Col>
          </Row>
          
          <Form.Item
            name="systemDescription"
            label="系统描述"
          >
            <Input.TextArea
              placeholder="请输入系统描述"
              rows={3}
            />
          </Form.Item>
        </Card>

        {/* 安全设置 */}
        <Card title="安全设置" style={{ marginBottom: 16 }}>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="passwordMinLength"
                label="密码最小长度"
                rules={[{ required: true, message: '请输入密码最小长度' }]}
              >
                <InputNumber
                  min={6}
                  max={20}
                  placeholder="请输入密码最小长度"
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="sessionTimeout"
                label="会话超时时间（分钟）"
                rules={[{ required: true, message: '请输入会话超时时间' }]}
              >
                <InputNumber
                  min={5}
                  max={1440}
                  placeholder="请输入会话超时时间"
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
          </Row>
          
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="enableTwoFactor"
                label="启用双因子认证"
                valuePropName="checked"
              >
                <Switch />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="enableLoginLog"
                label="启用登录日志"
                valuePropName="checked"
              >
                <Switch />
              </Form.Item>
            </Col>
          </Row>
        </Card>

        {/* 邮件设置 */}
        <Card title="邮件设置" style={{ marginBottom: 16 }}>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="smtpHost"
                label="SMTP服务器"
              >
                <Input placeholder="请输入SMTP服务器地址" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="smtpPort"
                label="SMTP端口"
              >
                <InputNumber
                  min={1}
                  max={65535}
                  placeholder="请输入SMTP端口"
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
          </Row>
          
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="smtpUsername"
                label="SMTP用户名"
              >
                <Input placeholder="请输入SMTP用户名" />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="smtpPassword"
                label="SMTP密码"
              >
                <Input.Password placeholder="请输入SMTP密码" />
              </Form.Item>
            </Col>
          </Row>
          
          <Form.Item
            name="enableEmailNotification"
            label="启用邮件通知"
            valuePropName="checked"
          >
            <Switch />
          </Form.Item>
        </Card>

        {/* 数据设置 */}
        <Card title="数据设置" style={{ marginBottom: 16 }}>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="backupInterval"
                label="自动备份间隔（天）"
                rules={[{ required: true, message: '请输入自动备份间隔' }]}
              >
                <InputNumber
                  min={1}
                  max={365}
                  placeholder="请输入自动备份间隔"
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
            <Col span={12}>
              <Form.Item
                name="maxBackupFiles"
                label="最大备份文件数"
                rules={[{ required: true, message: '请输入最大备份文件数' }]}
              >
                <InputNumber
                  min={1}
                  max={100}
                  placeholder="请输入最大备份文件数"
                  style={{ width: '100%' }}
                />
              </Form.Item>
            </Col>
          </Row>
          
          <Form.Item
            name="enableDataExport"
            label="启用数据导出"
            valuePropName="checked"
          >
            <Switch />
          </Form.Item>
        </Card>

        <Divider />

        <div style={{ textAlign: 'center' }}>
          <Button
            type="primary"
            htmlType="submit"
            icon={<SaveOutlined />}
            loading={loading}
            size="large"
            style={{ marginRight: 16 }}
          >
            保存设置
          </Button>
          <Button
            icon={<ReloadOutlined />}
            onClick={handleReset}
            size="large"
          >
            重置
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default SystemSettings;
