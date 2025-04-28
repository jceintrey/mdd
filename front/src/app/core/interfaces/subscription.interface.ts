
/**
 * Response shape for response returned by the backend on subscriptions retrieve call.
 *
 * @remarks
 * - the subscription link a user to a topic
 * - topic_name is added by backend to avoid a second api call
 *
 * @property id – Unique identifier of the topic
 * @property user_id – Unique identifier of the authenticated user
 * @property topic_id – Unique identifier of the topic
 * @property topic_name – The associated topic name
 *
 */
export interface Subscription {
  "id": number,
  "user_id": number,
  "topic_id": number,
  "topic_name": string
}
