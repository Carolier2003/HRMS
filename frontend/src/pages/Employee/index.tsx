import { PlusOutlined, DownloadOutlined } from '@ant-design/icons';
import { ProTable, ProColumns } from '@ant-design/pro-components';
import { Button, message, Modal, Tag } from 'antd';
import { useAccess } from '@umijs/max';
import { useRef, useState } from 'react';
import {
  queryEmployees,
  deleteEmployee,
  changeEmployeeStatus,
  exportEmployees,
} from '@/services/api';
import EmployeeModal from './components/EmployeeModal';

export default () => {
  const access = useAccess();
  const actionRef = useRef<any>();
  const [modalVisible, setModalVisible] = useState(false);
  const [currentRecord, setCurrentRecord] = useState<API.Employee | undefined>(undefined);

  const columns: ProColumns<API.Employee>[] = [
    {
      title: '工号',
      dataIndex: 'employeeNo',
      width: 100,
    },
    {
      title: '姓名',
      dataIndex: 'name',
      width: 100,
    },
    {
      title: '性别',
      dataIndex: 'gender',
      width: 60,
      hideInSearch: true,
    },
    {
      title: '联系电话',
      dataIndex: 'phone',
      width: 120,
      hideInSearch: true,
    },
    {
      title: '学历',
      dataIndex: 'education',
      width: 80,
      valueType: 'select',
      valueEnum: {
        '博士': { text: '博士' },
        '硕士': { text: '硕士' },
        '本科': { text: '本科' },
        '专科': { text: '专科' },
        '高中': { text: '高中' },
      },
    },
    {
      title: '婚姻状况',
      dataIndex: 'maritalStatus',
      width: 90,
      valueType: 'select',
      valueEnum: {
        '未婚': { text: '未婚' },
        '已婚': { text: '已婚' },
        '离异': { text: '离异' },
        '丧偶': { text: '丧偶' },
      },
    },
    {
      title: '部门',
      dataIndex: 'department',
      width: 100,
    },
    {
      title: '岗位',
      dataIndex: 'position',
      width: 120,
    },
    {
      title: '入职日期',
      dataIndex: 'entryDate',
      width: 110,
      hideInSearch: true,
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: 80,
      valueType: 'select',
      valueEnum: {
        '在职': { text: '在职', status: 'Success' },
        '离职': { text: '离职', status: 'Default' },
        '退休': { text: '退休', status: 'Warning' },
        '辞退': { text: '辞退', status: 'Error' },
      },
      render: (_, record) => {
        const colorMap: any = {
          '在职': 'green',
          '离职': 'default',
          '退休': 'orange',
          '辞退': 'red',
        };
        return <Tag color={colorMap[record.status || '在职']}>{record.status}</Tag>;
      },
    },
    {
      title: '操作',
      width: 240,
      fixed: 'right',
      hideInSearch: true,
      render: (_, record) => (
        <div style={{ display: 'flex', gap: '8px', flexWrap: 'wrap' }}>
          {access.canManageEmployee && (
            <Button
              type="link"
              size="small"
              onClick={() => {
                setCurrentRecord(record);
                setModalVisible(true);
              }}
              style={{ padding: '4px 8px' }}
            >
              编辑
            </Button>
          )}
          {access.canManageEmployee && record.status === '在职' && (
            <Button
              type="link"
              size="small"
              danger
              onClick={() => {
                Modal.confirm({
                  title: '变更员工状态',
                  content: '请选择离职类型',
                  okText: '确认',
                  cancelText: '取消',
                  onOk: () => {
                    Modal.confirm({
                      title: '选择离职类型',
                      content: (
                        <div style={{ display: 'flex', gap: '8px', flexDirection: 'column' }}>
                          <Button onClick={() => handleStatusChange(record.id!, '离职')}>离职</Button>
                          <Button onClick={() => handleStatusChange(record.id!, '退休')}>退休</Button>
                          <Button onClick={() => handleStatusChange(record.id!, '辞退')}>辞退</Button>
                        </div>
                      ),
                    });
                  },
                });
              }}
              style={{ padding: '4px 8px' }}
            >
              离职
            </Button>
          )}
          {access.isAdmin && (
            <Button
              type="link"
              size="small"
              danger
              onClick={() => {
                Modal.confirm({
                  title: '确认删除',
                  content: `确定要删除员工 ${record.name} 吗？`,
                  okText: '确认',
                  cancelText: '取消',
                  onOk: async () => {
                    const response = await deleteEmployee(record.id!);
                    if (response.code === 200) {
                      message.success('删除成功');
                      actionRef.current?.reload();
                    } else {
                      message.error(response.msg || '删除失败');
                    }
                  },
                });
              }}
              style={{ padding: '4px 8px' }}
            >
              删除
            </Button>
          )}
        </div>
      ),
    },
  ];

  const handleStatusChange = async (id: number, status: string, reason?: string) => {
    const response = await changeEmployeeStatus(id, status, reason);
    if (response.code === 200) {
      message.success('状态变更成功');
      actionRef.current?.reload();
    } else {
      message.error(response.msg || '状态变更失败');
    }
  };

  const handleExport = async (params: any) => {
    try {
      const blob = await exportEmployees(params);
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `员工信息表_${new Date().getTime()}.xlsx`;
      a.click();
      window.URL.revokeObjectURL(url);
      message.success('导出成功');
    } catch (error) {
      message.error('导出失败');
    }
  };

  return (
    <>
      <ProTable<API.Employee>
        columns={columns}
        actionRef={actionRef}
        request={async (params) => {
          const response = await queryEmployees({
            ...params,
            pageNum: params.current,
            pageSize: params.pageSize,
          });
          return {
            data: response.data.list,
            total: response.data.total,
            success: response.code === 200,
          };
        }}
        rowKey="id"
        search={{
          labelWidth: 'auto',
        }}
        pagination={{
          pageSize: 10,
        }}
        scroll={{ x: 1500 }}
        toolBarRender={(action, { selectedRowKeys }) => [
          access.canManageEmployee && (
            <Button
              type="primary"
              key="primary"
              icon={<PlusOutlined />}
              onClick={() => {
                setCurrentRecord(undefined);
                setModalVisible(true);
              }}
            >
              新增员工
            </Button>
          ),
          access.canManageEmployee && (
            <Button
              key="export"
              icon={<DownloadOutlined />}
              onClick={() => {
                const params = action?.pageInfo;
                handleExport(params);
              }}
            >
              导出 Excel
            </Button>
          ),
        ]}
      />
      
      <EmployeeModal
        visible={modalVisible}
        record={currentRecord}
        onCancel={() => {
          setModalVisible(false);
          setCurrentRecord(undefined);
        }}
        onSuccess={() => {
          setModalVisible(false);
          setCurrentRecord(undefined);
          actionRef.current?.reload();
        }}
      />
    </>
  );
};

