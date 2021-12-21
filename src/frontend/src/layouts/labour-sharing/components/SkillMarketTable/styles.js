import { makeStyles } from "@mui/styles";

/**
 * {@code SkillMarketTable} creates the component for the skill market table.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default makeStyles(({ functions, borders, palette }) => {
  const { white } = palette;
  const { pxToRem } = functions;
  const { borderWidth, borderColor } = borders;

  return {
    projects_table: {
      "& .MuiTableRow-root:not(:last-child)": {
        "& td": {
          borderBottom: `${borderWidth[1]} solid ${borderColor}`,
        },
      },
    },

    projects_tableAvatar: {
      border: `${borderWidth[2]} solid ${white.main}`,
      cursor: "pointer",
      position: "relative",

      "&:not(:first-child)": {
        marginLeft: pxToRem(-12),
      },

      "&:hover, &:focus": {
        zIndex: "10",
      },
    },
  };
});
