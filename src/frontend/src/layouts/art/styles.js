import { makeStyles } from "@mui/styles";

/**
 * {@code organisation} creates the layout for the CRUD interface.
 *
 * @author Teo Keng Swee
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
