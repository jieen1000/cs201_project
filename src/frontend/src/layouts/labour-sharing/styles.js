/**
 * {@code labour-sharing} creates the layout for the labour sharing marketplace.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

import { makeStyles } from "@mui/styles";

export default makeStyles(({ borders }) => {
  const { borderWidth, borderColor } = borders;

  return {
    tables_table: {
      "& .MuiTableRow-root:not(:last-child)": {
        "& td": {
          borderBottom: `${borderWidth[1]} solid ${borderColor}`,
        },
      },
    },
  };
});
