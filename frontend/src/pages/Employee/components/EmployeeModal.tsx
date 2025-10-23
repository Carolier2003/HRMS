import { Modal, Form, Input, Select, DatePicker, message, Row, Col } from 'antd';
import { useEffect } from 'react';
import { addEmployee, updateEmployee } from '@/services/api';
import moment from 'moment';

const { Option } = Select;

interface EmployeeModalProps {
  visible: boolean;
  record?: API.Employee;
  onCancel: () => void;
  onSuccess: () => void;
}

export default ({ visible, record, onCancel, onSuccess }: EmployeeModalProps) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (visible && record) {
      form.setFieldsValue({
        ...record,
        birthDate: record.birthDate ? moment(record.birthDate) : null,
        graduateDate: record.graduateDate ? moment(record.graduateDate) : null,
        entryDate: record.entryDate ? moment(record.entryDate) : null,
      });
    } else {
      form.resetFields();
    }
  }, [visible, record]);

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      const data = {
        ...values,
        birthDate: values.birthDate?.format('YYYY-MM-DD'),
        graduateDate: values.graduateDate?.format('YYYY-MM-DD'),
        entryDate: values.entryDate?.format('YYYY-MM-DD'),
      };

      let response;
      if (record?.id) {
        response = await updateEmployee(record.id, data);
      } else {
        response = await addEmployee(data);
      }

      if (response.code === 200) {
        message.success(record ? '更新成功' : '添加成功');
        onSuccess();
      } else {
        message.error(response.msg || '操作失败');
      }
    } catch (error) {
      console.error('表单验证失败', error);
    }
  };

  return (
    <Modal
      title={record ? '编辑员工' : '新增员工'}
      open={visible}
      onOk={handleSubmit}
      onCancel={onCancel}
      width={800}
      destroyOnClose
    >
      <Form form={form} layout="vertical">
        <Row gutter={16}>
          <Col span={12}>
            <Form.Item name="employeeNo" label="工号" rules={[{ required: true }]}>
              <Input placeholder="请输入工号" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="name" label="姓名" rules={[{ required: true }]}>
              <Input placeholder="请输入姓名" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item name="gender" label="性别">
              <Select placeholder="请选择性别">
                <Option value="男">男</Option>
                <Option value="女">女</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="birthDate" label="出生日期">
              <DatePicker style={{ width: '100%' }} />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item name="phone" label="联系电话">
              <Input placeholder="请输入联系电话" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="email" label="邮箱">
              <Input placeholder="请输入邮箱" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item name="education" label="学历">
              <Select placeholder="请选择学历">
                <Option value="博士">博士</Option>
                <Option value="硕士">硕士</Option>
                <Option value="本科">本科</Option>
                <Option value="专科">专科</Option>
                <Option value="高中">高中</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="maritalStatus" label="婚姻状况">
              <Select placeholder="请选择婚姻状况">
                <Option value="未婚">未婚</Option>
                <Option value="已婚">已婚</Option>
                <Option value="离异">离异</Option>
                <Option value="丧偶">丧偶</Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item name="department" label="部门">
              <Input placeholder="请输入部门" />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="position" label="岗位">
              <Input placeholder="请输入岗位" />
            </Form.Item>
          </Col>
        </Row>

        <Row gutter={16}>
          <Col span={12}>
            <Form.Item name="entryDate" label="入职日期">
              <DatePicker style={{ width: '100%' }} />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="status" label="状态" initialValue="在职">
              <Select>
                <Option value="在职">在职</Option>
                <Option value="离职">离职</Option>
                <Option value="退休">退休</Option>
                <Option value="辞退">辞退</Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </Modal>
  );
};

