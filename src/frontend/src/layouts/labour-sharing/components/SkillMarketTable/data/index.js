import SuiBox from "components/SuiBox";
import SuiTypography from "components/SuiTypography";
import SuiAvatar from "components/SuiAvatar";
import SuiBadge from "components/SuiBadge";
import Button from "@mui/material/Button";
import team2 from "assets/images/team-2.jpg";
import team3 from "assets/images/team-3.jpg";
import team4 from "assets/images/team-4.jpg";
import { Rating } from "@mui/material";

/**
 * {@code SkillMarketTable/data} provides static data for the Skill Market Table.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

function Labour({ image, name, company }) {
  return (
    <SuiBox display="flex" alignItems="center" px={1} py={0.5}>
      <SuiBox mr={2}>
        <SuiAvatar src={image} alt={name} size="sm" variant="rounded" />
      </SuiBox>
      <SuiBox display="flex" flexDirection="column">
        <SuiTypography variant="button" fontWeight="medium">
          {name}
        </SuiTypography>
        <SuiTypography variant="caption" textColor="secondary">
          {company}
        </SuiTypography>
      </SuiBox>
    </SuiBox>
  );
}

function Function({ category, experience }) {
  return (
    <SuiBox display="flex" flexDirection="column">
      <SuiTypography variant="caption" fontWeight="medium" textColor="text">
        {category}
      </SuiTypography>
      <SuiTypography variant="caption" textColor="secondary">
        {experience}
      </SuiTypography>
    </SuiBox>
  );
}

export default {
  columns: [
    { name: "Professional", align: "left" },
    { name: "Skill", align: "left" },
    { name: "Rating", align: "center" },
    { name: "Status", align: "center" },
    { name: "Action", align: "center" },
  ],

  rows: [
    {
      Professional: <Labour image={team2} name="John Michael" company="Merdeka Sdn Bhd" />,
      Skill: <Function category="Welding" experience="10 Years" />,
      Status: (
        <SuiBadge variant="gradient" badgeContent="for hire" color="success" size="extra-small" />
      ),
      Rating: (
        <Rating name="readOnly" value={3.7} precision={0.1} readOnly />
      ),
      Action: (
        <Button href="#text-buttons">View</Button>
      ),
    },
    {
      Professional: <Labour image={team3} name="Alexa Liras" company="Deckard Construction Pte Ltd" />,
      Skill: <Function category="Tower Crane Operation" experience="2 Years" />,
      Status: (
        <SuiBadge variant="gradient" badgeContent="hired" color="secondary" size="extra-small" />
      ),
      Rating: (
        <Rating name="readOnly" value={4.0} precision={0.1} readOnly />
      ),
      Action: (
        <Button href="#text-buttons">View</Button>
      ),
    },
    {
      Professional: <Labour image={team4} name="Laurent Perrier" company="Blackrock Heavy Industries Pte Ltd" />,
      Skill: <Function category="Gas Pipefitting" experience="3 Years" />,
      Status: (
        <SuiBadge variant="gradient" badgeContent="for hire" color="success" size="extra-small" />
      ),
      Rating: (
        <Rating name="readOnly" value={4.1} precision={0.1} readOnly />
      ),
      Action: (
        <Button href="#text-buttons">View</Button>
      ),
    },
    {
      Professional: <Labour image={team3} name="Michael Levi" company="Singapore Construction Pte Ltd" />,
      Skill: <Function category="Welding" experience="1 Year" />,
      Status: (
        <SuiBadge variant="gradient" badgeContent="for hire" color="success" size="extra-small" />
      ),
      Rating: (
        <Rating name="readOnly" value={4.1} precision={0.1} readOnly />
      ),
      Action: (
        <Button href="#text-buttons">View</Button>
      ),
    },
    {
      Professional: <Labour image={team2} name="Richard Gran" company="Terraformers Pte Ltd" />,
      Skill: <Function category="Lift Installation" experience="6 Years" />,
      Status: (
        <SuiBadge variant="gradient" badgeContent="hired" color="secondary" size="extra-small" />
      ),
      Rating: (
        <Rating name="readOnly" value={3.5} precision={0.1} readOnly />
      ),
      Action: (
        <Button href="#text-buttons">View</Button>
      ),
    },
    {
      Professional: <Labour image={team4} name="Miriam Eric" company="Penta Ocean Construction Pte Ltd" />,
      Skill: <Function category="Electrical Wiring Installation" experience="2 Years" />,
      Status: (
        <SuiBadge variant="gradient" badgeContent="hired" color="secondary" size="extra-small" />
      ),
      Rating: (
        <Rating name="readOnly" value={4.3} precision={0.1} readOnly />
      ),
      Action: (
        <Button href="#text-buttons">View</Button>
      ),
    },
  ],
};
