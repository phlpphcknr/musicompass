import styled from "styled-components/macro";

export default function RecommendationNotAvailable(){

    return (
        <Overview>
            <h4>sorry, no artist was found for your criteria</h4>
        </Overview>
    )
}

const Overview = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;

`