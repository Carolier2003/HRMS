import { Card, Row, Col, Statistic, Table, Button, message } from 'antd';
import { UserOutlined, TeamOutlined, FileTextOutlined, DownloadOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { Column, Pie } from '@ant-design/plots';
import { getStatistics, exportEmployees } from '@/services/api';

export default () => {
  const [loading, setLoading] = useState(false);
  const [statistics, setStatistics] = useState<API.StatisticsOverview>({
    totalEmployees: 0,
    onJobCount: 0,
    leaveCount: 0,
    educationStats: [],
    maritalStats: [],
    positionStats: [],
    departmentStats: [],
  });

  useEffect(() => {
    fetchStatistics();
  }, []);

  const fetchStatistics = async () => {
    setLoading(true);
    try {
      const response = await getStatistics();
      if (response.code === 200) {
        setStatistics(response.data);
      }
    } catch (error) {
      message.error('获取统计数据失败');
    } finally {
      setLoading(false);
    }
  };

  const handleExport = async () => {
    try {
      const blob = await exportEmployees({});
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `员工统计报表_${new Date().getTime()}.xlsx`;
      a.click();
      window.URL.revokeObjectURL(url);
      message.success('导出成功');
    } catch (error) {
      message.error('导出失败');
    }
  };

  // 学历统计柱状图配置
  const educationConfig = {
    data: statistics.educationStats || [],
    xField: 'name',
    yField: 'value',
    label: {
      position: 'middle' as const,
      style: {
        fill: '#FFFFFF',
        opacity: 0.6,
      },
    },
    xAxis: {
      label: {
        autoHide: true,
        autoRotate: false,
      },
    },
    meta: {
      name: {
        alias: '学历',
      },
      value: {
        alias: '人数',
      },
    },
  };

  // 婚姻状况饼图配置
  const maritalConfig = {
    appendPadding: 10,
    data: statistics.maritalStats || [],
    angleField: 'value',
    colorField: 'name',
    radius: 0.8,
    label: {
      type: 'outer' as const,
      content: '{name} {percentage}',
    },
    interactions: [
      {
        type: 'pie-legend-active',
      },
      {
        type: 'element-active',
      },
    ],
  };

  // 部门统计柱状图配置
  const departmentConfig = {
    data: statistics.departmentStats || [],
    xField: 'name',
    yField: 'value',
    label: {
      position: 'middle' as const,
      style: {
        fill: '#FFFFFF',
        opacity: 0.6,
      },
    },
    meta: {
      name: {
        alias: '部门',
      },
      value: {
        alias: '人数',
      },
    },
  };

  // 岗位统计表格列配置
  const positionColumns = [
    {
      title: '岗位',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '人数',
      dataIndex: 'value',
      key: 'value',
      sorter: (a: any, b: any) => a.value - b.value,
    },
  ];

  return (
    <div style={{ padding: '24px' }}>
      {/* 概览卡片 */}
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={8}>
          <Card>
            <Statistic
              title="员工总数"
              value={statistics.totalEmployees}
              prefix={<TeamOutlined />}
              valueStyle={{ color: '#3f8600' }}
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="在职人数"
              value={statistics.onJobCount}
              prefix={<UserOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </Card>
        </Col>
        <Col span={8}>
          <Card>
            <Statistic
              title="离职人数"
              value={statistics.leaveCount}
              prefix={<FileTextOutlined />}
              valueStyle={{ color: '#cf1322' }}
            />
          </Card>
        </Col>
      </Row>

      {/* 导出按钮 */}
      <Row style={{ marginBottom: 16 }}>
        <Col span={24} style={{ textAlign: 'right' }}>
          <Button type="primary" icon={<DownloadOutlined />} onClick={handleExport}>
            导出统计报表
          </Button>
        </Col>
      </Row>

      {/* 学历统计 */}
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={24}>
          <Card title="学历统计" loading={loading}>
            <Column {...educationConfig} />
          </Card>
        </Col>
      </Row>

      {/* 婚姻状况统计 */}
      <Row gutter={16} style={{ marginBottom: 24 }}>
        <Col span={12}>
          <Card title="婚姻状况统计" loading={loading}>
            <Pie {...maritalConfig} />
          </Card>
        </Col>
        <Col span={12}>
          <Card title="部门统计" loading={loading}>
            <Column {...departmentConfig} />
          </Card>
        </Col>
      </Row>

      {/* 岗位统计表格 */}
      <Row gutter={16}>
        <Col span={24}>
          <Card title="岗位统计" loading={loading}>
            <Table
              columns={positionColumns}
              dataSource={statistics.positionStats || []}
              rowKey="name"
              pagination={false}
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};
