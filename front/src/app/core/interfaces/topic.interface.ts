/**
 * Represents a topic or category that posts can belong to.
 *
 * @remarks
 * - The `subscribed` flag indicates whether the current user is subscribed to this topic.
 *
 * @property id – Unique identifier of the topic.
 * @property name – Display name of the topic.
 * @property description – Brief description explaining the topic’s purpose.
 * @property subscribed – Boolean flag; `true` if the user is subscribed, `false` otherwise.
 */
export interface Topic {
  id: number;
  name: string;
  description: string;
  subscribed: boolean;
}


