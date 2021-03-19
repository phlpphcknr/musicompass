import { useParams } from 'react-router-dom'

export default function ArtistOverview() {
  const { artistName } = useParams()

  return <section>Hello Artist</section>
}
