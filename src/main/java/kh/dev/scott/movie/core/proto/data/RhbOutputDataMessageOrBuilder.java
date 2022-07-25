// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RhbOutputDataMessage.proto

package kh.dev.scott.movie.core.proto.data;

public interface RhbOutputDataMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:kh.dev.scott.movie.core.proto.data.RhbOutputDataMessage)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 id = 1;</code>
   */
  long getId();

  /**
   * <code>string title = 2;</code>
   */
  java.lang.String getTitle();
  /**
   * <code>string title = 2;</code>
   */
  com.google.protobuf.ByteString
      getTitleBytes();

  /**
   * <code>string category = 3;</code>
   */
  java.lang.String getCategory();
  /**
   * <code>string category = 3;</code>
   */
  com.google.protobuf.ByteString
      getCategoryBytes();

  /**
   * <code>string star = 4;</code>
   */
  java.lang.String getStar();
  /**
   * <code>string star = 4;</code>
   */
  com.google.protobuf.ByteString
      getStarBytes();

  /**
   * <code>repeated .kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage items = 5;</code>
   */
  java.util.List<kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage> 
      getItemsList();
  /**
   * <code>repeated .kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage items = 5;</code>
   */
  kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage getItems(int index);
  /**
   * <code>repeated .kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage items = 5;</code>
   */
  int getItemsCount();
  /**
   * <code>repeated .kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage items = 5;</code>
   */
  java.util.List<? extends kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessageOrBuilder> 
      getItemsOrBuilderList();
  /**
   * <code>repeated .kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessage items = 5;</code>
   */
  kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessageOrBuilder getItemsOrBuilder(
      int index);

  /**
   * <code>string create_at = 6;</code>
   */
  java.lang.String getCreateAt();
  /**
   * <code>string create_at = 6;</code>
   */
  com.google.protobuf.ByteString
      getCreateAtBytes();

  /**
   * <code>string update_at = 7;</code>
   */
  java.lang.String getUpdateAt();
  /**
   * <code>string update_at = 7;</code>
   */
  com.google.protobuf.ByteString
      getUpdateAtBytes();

  /**
   * <code>string timestamp = 8;</code>
   */
  java.lang.String getTimestamp();
  /**
   * <code>string timestamp = 8;</code>
   */
  com.google.protobuf.ByteString
      getTimestampBytes();

  /**
   * <code>string traceNo = 9;</code>
   */
  java.lang.String getTraceNo();
  /**
   * <code>string traceNo = 9;</code>
   */
  com.google.protobuf.ByteString
      getTraceNoBytes();
}
