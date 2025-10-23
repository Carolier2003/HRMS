import { ProTable, ProColumns } from '@ant-design/pro-components';
import { Button, Modal, Descriptions, message } from 'antd';
import { DownloadOutlined, EyeOutlined } from '@ant-design/icons';
import { useRef, useState } from 'react';
import { useModel } from '@umijs/max';
import { getEmployeeSalaryRecords, getSalaryDetail, exportSalaryPdf } from '@/services/api';

export default () => {
  const { initialState } = useModel('@@initialState');
  const actionRef = useRef<any>();
  const [detailVisible, setDetailVisible] = useState(false);
  const [currentDetail, setCurrentDetail] = useState<API.SalaryDetailVO | undefined>(undefined);

  const columns: ProColumns<API.SalaryRecord>[] = [
    {
      title: '年月',
      dataIndex: 'yearMonth',
      width: 100,
    },
    {
      title: '基本工资',
      dataIndex: 'baseSalary',
      width: 120,
      valueType: 'money',
    },
    {
      title: '奖金',
      dataIndex: 'bonus',
      width: 120,
      valueType: 'money',
    },
    {
      title: '扣款',
      dataIndex: 'deduction',
      width: 120,
      valueType: 'money',
    },
    {
      title: '实发工资',
      dataIndex: 'actualSalary',
      width: 120,
      valueType: 'money',
      render: (_, record) => (
        <span style={{ color: '#52c41a', fontWeight: 'bold' }}>
          ¥{record.actualSalary ? record.actualSalary.toFixed(2) : '0.00'}
        </span>
      ),
    },
    {
      title: '操作',
      width: 150,
      fixed: 'right',
      hideInSearch: true,
      render: (_, record) => [
        <a
          key="detail"
          onClick={async () => {
            const response = await getSalaryDetail(record.id!);
            if (response.code === 200) {
              setCurrentDetail(response.data);
              setDetailVisible(true);
            }
          }}
        >
          <EyeOutlined /> 查看明细
        </a>,
        <a
          key="pdf"
          onClick={async () => {
            try {
              const blob = await exportSalaryPdf(record.id!);
              const url = window.URL.createObjectURL(blob);
              const a = document.createElement('a');
              a.href = url;
              a.download = `工资条_${record.yearMonth}.pdf`;
              a.click();
              window.URL.revokeObjectURL(url);
              message.success('导出成功');
            } catch (error) {
              message.error('导出失败');
            }
          }}
        >
          <DownloadOutlined /> 导出PDF
        </a>,
      ],
    },
  ];

  return (
    <>
      <ProTable<API.SalaryRecord>
        columns={columns}
        actionRef={actionRef}
        request={async () => {
          const employeeId = initialState?.currentUser?.userId;
          if (!employeeId) {
            return { data: [], success: false };
          }
          const response = await getEmployeeSalaryRecords(employeeId);
          return {
            data: response.data,
            success: response.code === 200,
          };
        }}
        rowKey="id"
        search={false}
        pagination={{
          pageSize: 10,
        }}
      />

      <Modal
        title="工资明细"
        open={detailVisible}
        onCancel={() => setDetailVisible(false)}
        footer={null}
        width={600}
      >
        {currentDetail && (
          <Descriptions column={2} bordered>
            <Descriptions.Item label="年月" span={2}>
              {currentDetail.yearMonth}
            </Descriptions.Item>
            <Descriptions.Item label="基本工资">
              ¥{currentDetail.baseSalary ? currentDetail.baseSalary.toFixed(2) : '0.00'}
            </Descriptions.Item>
            <Descriptions.Item label="奖金">
              ¥{currentDetail.bonus ? currentDetail.bonus.toFixed(2) : '0.00'}
            </Descriptions.Item>
            <Descriptions.Item label="扣款">
              ¥{currentDetail.deduction ? currentDetail.deduction.toFixed(2) : '0.00'}
            </Descriptions.Item>
            <Descriptions.Item label="实发工资">
              <span style={{ color: '#52c41a', fontWeight: 'bold' }}>
                ¥{currentDetail.actualSalary ? currentDetail.actualSalary.toFixed(2) : '0.00'}
              </span>
            </Descriptions.Item>
            {currentDetail.items && currentDetail.items.length > 0 && (
              <Descriptions.Item label="明细项目" span={2}>
                {currentDetail.items.map((item, index) => (
                  <div key={index}>
                    {item.itemName}: ¥{item.amount ? item.amount.toFixed(2) : '0.00'}
                  </div>
                ))}
              </Descriptions.Item>
            )}
            {currentDetail.remark && (
              <Descriptions.Item label="备注" span={2}>
                {currentDetail.remark}
              </Descriptions.Item>
            )}
          </Descriptions>
        )}
      </Modal>
    </>
  );
};

