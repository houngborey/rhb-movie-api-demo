// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RhbOutputDataMessage.proto

package kh.dev.scott.movie.core.proto.data;

public final class RhbOutputDataMessageOuterClass {
  private RhbOutputDataMessageOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_kh_dev_scott_movie_core_proto_data_RhbOutputDataMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_kh_dev_scott_movie_core_proto_data_RhbOutputDataMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\032RhbOutputDataMessage.proto\022\"kh.dev.sco" +
      "tt.movie.core.proto.data\032\036RhbOutputDataI" +
      "temMessage.proto\"\355\001\n\024RhbOutputDataMessag" +
      "e\022\n\n\002id\030\001 \001(\003\022\r\n\005title\030\002 \001(\t\022\020\n\010category" +
      "\030\003 \001(\t\022\014\n\004star\030\004 \001(\t\022P\n\005items\030\005 \003(\0132A.kh" +
      ".dev.scott.movie.core.proto.data.item.Rh" +
      "bOutputDataItemMessage\022\021\n\tcreate_at\030\006 \001(" +
      "\t\022\021\n\tupdate_at\030\007 \001(\t\022\021\n\ttimestamp\030\010 \001(\t\022" +
      "\017\n\007traceNo\030\t \001(\tB\002P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessageOuterClass.getDescriptor(),
        }, assigner);
    internal_static_kh_dev_scott_movie_core_proto_data_RhbOutputDataMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_kh_dev_scott_movie_core_proto_data_RhbOutputDataMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_kh_dev_scott_movie_core_proto_data_RhbOutputDataMessage_descriptor,
        new java.lang.String[] { "Id", "Title", "Category", "Star", "Items", "CreateAt", "UpdateAt", "Timestamp", "TraceNo", });
    kh.dev.scott.movie.core.proto.data.item.RhbOutputDataItemMessageOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}