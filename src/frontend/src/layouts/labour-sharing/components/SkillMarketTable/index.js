import * as React from 'react';
import data from "layouts/labour-sharing/components/SkillMarketTable/data";
import DataTable from "react-data-table-component";
import DataTableExtensions from "react-data-table-component-extensions";
import SuiBox from 'components/SuiBox';

/**
 * {@code SkillMarketTable} creates the component for the skill market table.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default function SkillMarketTable() {

  const { columns, rows } = data;

  return (
    <SuiBox p={3}>
      <DataTableExtensions exportHeaders columns={columns} data={rows}>
        <DataTable
          noHeader
          defaultSortField="id"
          defaultSortAsc={false}
          pagination
          highlightOnHover
        />
      </DataTableExtensions>
    </SuiBox>
  );
}
