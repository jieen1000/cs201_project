import { makeStyles } from "@mui/styles";

/**
 * {@code categories} provides a layout for the categories page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

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
