import { PlusOutlined, DeleteOutlined } from '@ant-design/icons';
import { ProTable, ProColumns } from '@ant-design/pro-components';
import { Button, Modal, Form, InputNumber, Input, message, Select, Popconfirm } from 'antd';
import { useRef, useState, useEffect } from 'react';
import { useAccess } from '@umijs/max';
import { createSalaryRecord, getEmployeeSalaryRecords, queryEmployees, deleteSalaryRecord } from '@/services/api';
import moment from 'moment';

const { Option } = Select;

export default () => {
  const access = useAccess();
  const actionRef = useRef<any>();
  const [modalVisible, setModalVisible] = useState(false);
  const [employees, setEmployees] = useState<API.Employee[]>([]);
  const [form] = Form.useForm();

  useEffect(() => {
    // 加载员工列表
    loadEmployees();
  }, []);

  const loadEmployees = async () => {
    const response = await queryEmployees({ status: '在职', pageNum: 1, pageSize: 1000 });
    if (response.code === 200) {
      setEmployees(response.data.list);
    }
  };

  const columns: ProColumns<API.SalaryRecord>[] = [
    {
      title: '年月',
      dataIndex: 'yearMonth',
      width: 100,
    },
    {
      title: '员工姓名',
      dataIndex: 'employeeName',
      width: 120,
      hideInSearch: true,
    },
    {
      title: '工号',
      dataIndex: 'employeeNo',
      width: 100,
      hideInSearch: true,
    },
    {
      title: '员工ID',
      dataIndex: 'employeeId',
      width: 100,
      hideInTable: true,
    },
    {
      title: '基本工资',
      dataIndex: 'baseSalary',
      width: 120,
      valueType: 'money',
      hideInSearch: true,
    },
    {
      title: '奖金',
      dataIndex: 'bonus',
      width: 120,
      valueType: 'money',
      hideInSearch: true,
    },
    {
      title: '扣款',
      dataIndex: 'deduction',
      width: 120,
      valueType: 'money',
      hideInSearch: true,
    },
    {
      title: '实发工资',
      dataIndex: 'actualSalary',
      width: 120,
      valueType: 'money',
      hideInSearch: true,
      render: (_, record) => (
        <span style={{ color: '#52c41a', fontWeight: 'bold' }}>
          ¥{record.actualSalary ? record.actualSalary.toFixed(2) : '0.00'}
        </span>
      ),
    },
    {
      title: '备注',
      dataIndex: 'remark',
      width: 150,
      hideInSearch: true,
      render: (text) => text || '-',
    },
    {
      title: '操作',
      width: 100,
      fixed: 'right',
      hideInSearch: true,
      render: (_, record) => (
        access.canManageSalary && (
          <Popconfirm
            title="确认删除"
            description="确定要删除这条工资记录吗？"
            onConfirm={async () => {
              try {
                await deleteSalaryRecord(record.id!);
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
        )
      ),
    },
  ];

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      const actualSalary = values.baseSalary + values.bonus - values.deduction;
      
      const data: API.SalaryRecordRequest = {
        ...values,
        actualSalary,
        yearMonth: moment().format('YYYY-MM'),
      };

      const response = await createSalaryRecord(data);
      if (response.code === 200) {
        message.success('创建成功');
        setModalVisible(false);
        form.resetFields();
        actionRef.current?.reload();
      } else {
        message.error(response.msg || '创建失败');
      }
    } catch (error) {
      console.error('表单验证失败', error);
    }
  };

  return (
    <>
      <ProTable<API.SalaryRecord>
        columns={columns}
        actionRef={actionRef}
        request={async (params) => {
          try {
            // 获取所有员工的工资记录
            const response = await queryEmployees({ status: '在职', pageNum: 1, pageSize: 1000 });
            if (response.code === 200) {
              const allRecords: API.SalaryRecord[] = [];
              
              // 为每个员工获取工资记录
              for (const employee of response.data.list) {
                const salaryResponse = await getEmployeeSalaryRecords(employee.id!);
                if (salaryResponse.code === 200) {
                  // 为每条记录添加员工信息
                  const recordsWithEmployee = salaryResponse.data.map(record => ({
                    ...record,
                    employeeName: employee.name,
                    employeeNo: employee.employeeNo,
                  }));
                  allRecords.push(...recordsWithEmployee);
                }
              }
              
              // 按年月和员工ID排序
              allRecords.sort((a, b) => {
                if (a.yearMonth !== b.yearMonth) {
                  return b.yearMonth.localeCompare(a.yearMonth);
                }
                return (a.employeeId || 0) - (b.employeeId || 0);
              });
              
              return {
                data: allRecords,
                success: true,
                total: allRecords.length,
              };
            }
            return {
              data: [],
              success: false,
            };
          } catch (error) {
            console.error('加载工资记录失败:', error);
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
                setModalVisible(true);
              }}
            >
              录入工资
            </Button>
          ),
        ]}
      />

      <Modal
        title="录入工资"
        open={modalVisible}
        onOk={handleSubmit}
        onCancel={() => {
          setModalVisible(false);
          form.resetFields();
        }}
        width={600}
      >
        <Form form={form} layout="vertical">
          <Form.Item name="employeeId" label="员工" rules={[{ required: true }]}>
            <Select placeholder="请选择员工" showSearch filterOption={(input, option) =>
              (option?.children as unknown as string).toLowerCase().includes(input.toLowerCase())
            }>
              {employees.map((emp) => (
                <Option key={emp.id} value={emp.id}>
                  {emp.name} ({emp.employeeNo})
                </Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item name="baseSalary" label="基本工资" rules={[{ required: true }]}>
            <InputNumber
              style={{ width: '100%' }}
              min={0}
              precision={2}
              placeholder="请输入基本工资"
            />
          </Form.Item>

          <Form.Item name="bonus" label="奖金" initialValue={0}>
            <InputNumber
              style={{ width: '100%' }}
              min={0}
              precision={2}
              placeholder="请输入奖金"
            />
          </Form.Item>

          <Form.Item name="deduction" label="扣款" initialValue={0}>
            <InputNumber
              style={{ width: '100%' }}
              min={0}
              precision={2}
              placeholder="请输入扣款"
            />
          </Form.Item>

          <Form.Item name="remark" label="备注">
            <Input.TextArea placeholder="请输入备注" rows={3} />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

