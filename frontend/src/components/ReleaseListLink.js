import styled from 'styled-components/macro'
import {Link} from "react-router-dom";

export default function ReleaseListLink ({format, artistName}){

    return (
        <ReleaseBox>
            <p>Most popular & wanted {format}</p>
            <Link to={`artist/${artistName}/${format}s`}>
                <p>see more {format}s ...</p>
            </Link>
        </ReleaseBox>
    )
}

const ReleaseBox = styled.div`

  display: flex;
  flex-direction: column;
  background-color: var(--secondary-color);
  padding: 20px;
  
`