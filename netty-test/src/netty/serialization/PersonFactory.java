// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PersonFactory.proto

package netty.serialization;

public final class PersonFactory
{
	private PersonFactory()
	{
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry)
	{
	}

	public interface PersonOrBuilder extends com.google.protobuf.MessageOrBuilder
	{

		// required int32 age = 1;
		boolean hasAge();

		int getAge();

		// required string name = 2;
		boolean hasName();

		String getName();

		// repeated string address = 3;
		java.util.List<String> getAddressList();

		int getAddressCount();

		String getAddress(int index);

		// optional string sex = 4 [default = "\304\320"];
		boolean hasSex();

		String getSex();
	}

	public static final class Person extends com.google.protobuf.GeneratedMessage implements PersonOrBuilder
	{
		// Use Person.newBuilder() to construct.
		private Person(Builder builder)
		{
			super(builder);
		}

		private Person(boolean noInit)
		{
		}

		private static final Person defaultInstance;

		public static Person getDefaultInstance()
		{
			return defaultInstance;
		}

		public Person getDefaultInstanceForType()
		{
			return defaultInstance;
		}

		public static final com.google.protobuf.Descriptors.Descriptor getDescriptor()
		{
			return netty.serialization.PersonFactory.internal_static_Person_descriptor;
		}

		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
		{
			return netty.serialization.PersonFactory.internal_static_Person_fieldAccessorTable;
		}

		public enum Phones implements com.google.protobuf.ProtocolMessageEnum
		{
			PHONE1(0, 0), PHONE2(1, 1),;

			public static final int	PHONE1_VALUE	= 0;
			public static final int	PHONE2_VALUE	= 1;

			public final int getNumber()
			{
				return value;
			}

			public static Phones valueOf(int value)
			{
				switch (value)
				{
					case 0:
						return PHONE1;
					case 1:
						return PHONE2;
					default:
						return null;
				}
			}

			public static com.google.protobuf.Internal.EnumLiteMap<Phones> internalGetValueMap()
			{
				return internalValueMap;
			}

			private static com.google.protobuf.Internal.EnumLiteMap<Phones> internalValueMap = new com.google.protobuf.Internal.EnumLiteMap<Phones>()
			{
				public Phones findValueByNumber(int number)
				{
					return Phones.valueOf(number);
				}
			};

			public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor()
			{
				return getDescriptor().getValues().get(index);
			}

			public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType()
			{
				return getDescriptor();
			}

			public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor()
			{
				return netty.serialization.PersonFactory.Person.getDescriptor().getEnumTypes().get(0);
			}

			private static final Phones[] VALUES =
			{ PHONE1, PHONE2, };

			public static Phones valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc)
			{
				if (desc.getType() != getDescriptor())
				{
					throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
				}
				return VALUES[desc.getIndex()];
			}

			private final int	index;
			private final int	value;

			private Phones(int index, int value)
			{
				this.index = index;
				this.value = value;
			}

			// @@protoc_insertion_point(enum_scope:Person.Phones)
		}

		public interface StudentOrBuilder extends com.google.protobuf.MessageOrBuilder
		{

			// required string stu_name = 1;
			boolean hasStuName();

			String getStuName();

			// required int32 stu_age = 2;
			boolean hasStuAge();

			int getStuAge();
		}

		public static final class Student extends com.google.protobuf.GeneratedMessage implements StudentOrBuilder
		{
			// Use Student.newBuilder() to construct.
			private Student(Builder builder)
			{
				super(builder);
			}

			private Student(boolean noInit)
			{
			}

			private static final Student defaultInstance;

			public static Student getDefaultInstance()
			{
				return defaultInstance;
			}

			public Student getDefaultInstanceForType()
			{
				return defaultInstance;
			}

			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor()
			{
				return netty.serialization.PersonFactory.internal_static_Person_Student_descriptor;
			}

			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
			{
				return netty.serialization.PersonFactory.internal_static_Person_Student_fieldAccessorTable;
			}

			private int					bitField0_;
			// required string stu_name = 1;
			public static final int		STU_NAME_FIELD_NUMBER	= 1;
			private java.lang.Object	stuName_;

			public boolean hasStuName()
			{
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}

			public String getStuName()
			{
				java.lang.Object ref = stuName_;
				if (ref instanceof String)
				{
					return (String) ref;
				}
				else
				{
					com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
					String s = bs.toStringUtf8();
					if (com.google.protobuf.Internal.isValidUtf8(bs))
					{
						stuName_ = s;
					}
					return s;
				}
			}

			private com.google.protobuf.ByteString getStuNameBytes()
			{
				java.lang.Object ref = stuName_;
				if (ref instanceof String)
				{
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((String) ref);
					stuName_ = b;
					return b;
				}
				else
				{
					return (com.google.protobuf.ByteString) ref;
				}
			}

			// required int32 stu_age = 2;
			public static final int	STU_AGE_FIELD_NUMBER	= 2;
			private int				stuAge_;

			public boolean hasStuAge()
			{
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			public int getStuAge()
			{
				return stuAge_;
			}

			private void initFields()
			{
				stuName_ = "";
				stuAge_ = 0;
			}

			private byte memoizedIsInitialized = -1;

			public final boolean isInitialized()
			{
				byte isInitialized = memoizedIsInitialized;
				if (isInitialized != -1)
					return isInitialized == 1;

				if (!hasStuName())
				{
					memoizedIsInitialized = 0;
					return false;
				}
				if (!hasStuAge())
				{
					memoizedIsInitialized = 0;
					return false;
				}
				memoizedIsInitialized = 1;
				return true;
			}

			public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException
			{
				getSerializedSize();
				if (((bitField0_ & 0x00000001) == 0x00000001))
				{
					output.writeBytes(1, getStuNameBytes());
				}
				if (((bitField0_ & 0x00000002) == 0x00000002))
				{
					output.writeInt32(2, stuAge_);
				}
				getUnknownFields().writeTo(output);
			}

			private int memoizedSerializedSize = -1;

			public int getSerializedSize()
			{
				int size = memoizedSerializedSize;
				if (size != -1)
					return size;

				size = 0;
				if (((bitField0_ & 0x00000001) == 0x00000001))
				{
					size += com.google.protobuf.CodedOutputStream.computeBytesSize(1, getStuNameBytes());
				}
				if (((bitField0_ & 0x00000002) == 0x00000002))
				{
					size += com.google.protobuf.CodedOutputStream.computeInt32Size(2, stuAge_);
				}
				size += getUnknownFields().getSerializedSize();
				memoizedSerializedSize = size;
				return size;
			}

			private static final long serialVersionUID = 0L;

			@java.lang.Override
			protected java.lang.Object writeReplace() throws java.io.ObjectStreamException
			{
				return super.writeReplace();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException
			{
				return newBuilder().mergeFrom(data).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException
			{
				return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException
			{
				return newBuilder().mergeFrom(data).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException
			{
				return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(java.io.InputStream input) throws java.io.IOException
			{
				return newBuilder().mergeFrom(input).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
			{
				return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException
			{
				Builder builder = newBuilder();
				if (builder.mergeDelimitedFrom(input))
				{
					return builder.buildParsed();
				}
				else
				{
					return null;
				}
			}

			public static netty.serialization.PersonFactory.Person.Student parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
			{
				Builder builder = newBuilder();
				if (builder.mergeDelimitedFrom(input, extensionRegistry))
				{
					return builder.buildParsed();
				}
				else
				{
					return null;
				}
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException
			{
				return newBuilder().mergeFrom(input).buildParsed();
			}

			public static netty.serialization.PersonFactory.Person.Student parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
			{
				return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
			}

			public static Builder newBuilder()
			{
				return Builder.create();
			}

			public Builder newBuilderForType()
			{
				return newBuilder();
			}

			public static Builder newBuilder(netty.serialization.PersonFactory.Person.Student prototype)
			{
				return newBuilder().mergeFrom(prototype);
			}

			public Builder toBuilder()
			{
				return newBuilder(this);
			}

			@java.lang.Override
			protected Builder newBuilderForType(com.google.protobuf.GeneratedMessage.BuilderParent parent)
			{
				Builder builder = new Builder(parent);
				return builder;
			}

			public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements netty.serialization.PersonFactory.Person.StudentOrBuilder
			{
				public static final com.google.protobuf.Descriptors.Descriptor getDescriptor()
				{
					return netty.serialization.PersonFactory.internal_static_Person_Student_descriptor;
				}

				protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
				{
					return netty.serialization.PersonFactory.internal_static_Person_Student_fieldAccessorTable;
				}

				// Construct using
				// netty.serialization.PersonFactory.Person.Student.newBuilder()
				private Builder()
				{
					maybeForceBuilderInitialization();
				}

				private Builder(BuilderParent parent)
				{
					super(parent);
					maybeForceBuilderInitialization();
				}

				private void maybeForceBuilderInitialization()
				{
					if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders)
					{
					}
				}

				private static Builder create()
				{
					return new Builder();
				}

				public Builder clear()
				{
					super.clear();
					stuName_ = "";
					bitField0_ = (bitField0_ & ~0x00000001);
					stuAge_ = 0;
					bitField0_ = (bitField0_ & ~0x00000002);
					return this;
				}

				public Builder clone()
				{
					return create().mergeFrom(buildPartial());
				}

				public com.google.protobuf.Descriptors.Descriptor getDescriptorForType()
				{
					return netty.serialization.PersonFactory.Person.Student.getDescriptor();
				}

				public netty.serialization.PersonFactory.Person.Student getDefaultInstanceForType()
				{
					return netty.serialization.PersonFactory.Person.Student.getDefaultInstance();
				}

				public netty.serialization.PersonFactory.Person.Student build()
				{
					netty.serialization.PersonFactory.Person.Student result = buildPartial();
					if (!result.isInitialized())
					{
						throw newUninitializedMessageException(result);
					}
					return result;
				}

				private netty.serialization.PersonFactory.Person.Student buildParsed() throws com.google.protobuf.InvalidProtocolBufferException
				{
					netty.serialization.PersonFactory.Person.Student result = buildPartial();
					if (!result.isInitialized())
					{
						throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
					}
					return result;
				}

				public netty.serialization.PersonFactory.Person.Student buildPartial()
				{
					netty.serialization.PersonFactory.Person.Student result = new netty.serialization.PersonFactory.Person.Student(this);
					int from_bitField0_ = bitField0_;
					int to_bitField0_ = 0;
					if (((from_bitField0_ & 0x00000001) == 0x00000001))
					{
						to_bitField0_ |= 0x00000001;
					}
					result.stuName_ = stuName_;
					if (((from_bitField0_ & 0x00000002) == 0x00000002))
					{
						to_bitField0_ |= 0x00000002;
					}
					result.stuAge_ = stuAge_;
					result.bitField0_ = to_bitField0_;
					onBuilt();
					return result;
				}

				public Builder mergeFrom(com.google.protobuf.Message other)
				{
					if (other instanceof netty.serialization.PersonFactory.Person.Student)
					{
						return mergeFrom((netty.serialization.PersonFactory.Person.Student) other);
					}
					else
					{
						super.mergeFrom(other);
						return this;
					}
				}

				public Builder mergeFrom(netty.serialization.PersonFactory.Person.Student other)
				{
					if (other == netty.serialization.PersonFactory.Person.Student.getDefaultInstance())
						return this;
					if (other.hasStuName())
					{
						setStuName(other.getStuName());
					}
					if (other.hasStuAge())
					{
						setStuAge(other.getStuAge());
					}
					this.mergeUnknownFields(other.getUnknownFields());
					return this;
				}

				public final boolean isInitialized()
				{
					if (!hasStuName())
					{

						return false;
					}
					if (!hasStuAge())
					{

						return false;
					}
					return true;
				}

				public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
				{
					com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
					while (true)
					{
						int tag = input.readTag();
						switch (tag)
						{
							case 0:
								this.setUnknownFields(unknownFields.build());
								onChanged();
								return this;
							default:
							{
								if (!parseUnknownField(input, unknownFields, extensionRegistry, tag))
								{
									this.setUnknownFields(unknownFields.build());
									onChanged();
									return this;
								}
								break;
							}
							case 10:
							{
								bitField0_ |= 0x00000001;
								stuName_ = input.readBytes();
								break;
							}
							case 16:
							{
								bitField0_ |= 0x00000002;
								stuAge_ = input.readInt32();
								break;
							}
						}
					}
				}

				private int					bitField0_;

				// required string stu_name = 1;
				private java.lang.Object	stuName_	= "";

				public boolean hasStuName()
				{
					return ((bitField0_ & 0x00000001) == 0x00000001);
				}

				public String getStuName()
				{
					java.lang.Object ref = stuName_;
					if (!(ref instanceof String))
					{
						String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
						stuName_ = s;
						return s;
					}
					else
					{
						return (String) ref;
					}
				}

				public Builder setStuName(String value)
				{
					if (value == null)
					{
						throw new NullPointerException();
					}
					bitField0_ |= 0x00000001;
					stuName_ = value;
					onChanged();
					return this;
				}

				public Builder clearStuName()
				{
					bitField0_ = (bitField0_ & ~0x00000001);
					stuName_ = getDefaultInstance().getStuName();
					onChanged();
					return this;
				}

				void setStuName(com.google.protobuf.ByteString value)
				{
					bitField0_ |= 0x00000001;
					stuName_ = value;
					onChanged();
				}

				// required int32 stu_age = 2;
				private int stuAge_;

				public boolean hasStuAge()
				{
					return ((bitField0_ & 0x00000002) == 0x00000002);
				}

				public int getStuAge()
				{
					return stuAge_;
				}

				public Builder setStuAge(int value)
				{
					bitField0_ |= 0x00000002;
					stuAge_ = value;
					onChanged();
					return this;
				}

				public Builder clearStuAge()
				{
					bitField0_ = (bitField0_ & ~0x00000002);
					stuAge_ = 0;
					onChanged();
					return this;
				}

				// @@protoc_insertion_point(builder_scope:Person.Student)
			}

			static
			{
				defaultInstance = new Student(true);
				defaultInstance.initFields();
			}

			// @@protoc_insertion_point(class_scope:Person.Student)
		}

		private int				bitField0_;
		// required int32 age = 1;
		public static final int	AGE_FIELD_NUMBER	= 1;
		private int				age_;

		public boolean hasAge()
		{
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}

		public int getAge()
		{
			return age_;
		}

		// required string name = 2;
		public static final int		NAME_FIELD_NUMBER	= 2;
		private java.lang.Object	name_;

		public boolean hasName()
		{
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}

		public String getName()
		{
			java.lang.Object ref = name_;
			if (ref instanceof String)
			{
				return (String) ref;
			}
			else
			{
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				String s = bs.toStringUtf8();
				if (com.google.protobuf.Internal.isValidUtf8(bs))
				{
					name_ = s;
				}
				return s;
			}
		}

		private com.google.protobuf.ByteString getNameBytes()
		{
			java.lang.Object ref = name_;
			if (ref instanceof String)
			{
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((String) ref);
				name_ = b;
				return b;
			}
			else
			{
				return (com.google.protobuf.ByteString) ref;
			}
		}

		// repeated string address = 3;
		public static final int						ADDRESS_FIELD_NUMBER	= 3;
		private com.google.protobuf.LazyStringList	address_;

		public java.util.List<String> getAddressList()
		{
			return address_;
		}

		public int getAddressCount()
		{
			return address_.size();
		}

		public String getAddress(int index)
		{
			return address_.get(index);
		}

		// optional string sex = 4 [default = "\304\320"];
		public static final int		SEX_FIELD_NUMBER	= 4;
		private java.lang.Object	sex_;

		public boolean hasSex()
		{
			return ((bitField0_ & 0x00000004) == 0x00000004);
		}

		public String getSex()
		{
			java.lang.Object ref = sex_;
			if (ref instanceof String)
			{
				return (String) ref;
			}
			else
			{
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				String s = bs.toStringUtf8();
				if (com.google.protobuf.Internal.isValidUtf8(bs))
				{
					sex_ = s;
				}
				return s;
			}
		}

		private com.google.protobuf.ByteString getSexBytes()
		{
			java.lang.Object ref = sex_;
			if (ref instanceof String)
			{
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((String) ref);
				sex_ = b;
				return b;
			}
			else
			{
				return (com.google.protobuf.ByteString) ref;
			}
		}

		private void initFields()
		{
			age_ = 0;
			name_ = "";
			address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
			sex_ = com.google.protobuf.Internal.stringDefaultValue("\304\320");
		}

		private byte memoizedIsInitialized = -1;

		public final boolean isInitialized()
		{
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1)
				return isInitialized == 1;

			if (!hasAge())
			{
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasName())
			{
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}

		public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException
		{
			getSerializedSize();
			if (((bitField0_ & 0x00000001) == 0x00000001))
			{
				output.writeInt32(1, age_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002))
			{
				output.writeBytes(2, getNameBytes());
			}
			for (int i = 0; i < address_.size(); i++)
			{
				output.writeBytes(3, address_.getByteString(i));
			}
			if (((bitField0_ & 0x00000004) == 0x00000004))
			{
				output.writeBytes(4, getSexBytes());
			}
			getUnknownFields().writeTo(output);
		}

		private int memoizedSerializedSize = -1;

		public int getSerializedSize()
		{
			int size = memoizedSerializedSize;
			if (size != -1)
				return size;

			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001))
			{
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, age_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002))
			{
				size += com.google.protobuf.CodedOutputStream.computeBytesSize(2, getNameBytes());
			}
			{
				int dataSize = 0;
				for (int i = 0; i < address_.size(); i++)
				{
					dataSize += com.google.protobuf.CodedOutputStream.computeBytesSizeNoTag(address_.getByteString(i));
				}
				size += dataSize;
				size += 1 * getAddressList().size();
			}
			if (((bitField0_ & 0x00000004) == 0x00000004))
			{
				size += com.google.protobuf.CodedOutputStream.computeBytesSize(4, getSexBytes());
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}

		private static final long serialVersionUID = 0L;

		@java.lang.Override
		protected java.lang.Object writeReplace() throws java.io.ObjectStreamException
		{
			return super.writeReplace();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException
		{
			return newBuilder().mergeFrom(data).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException
		{
			return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException
		{
			return newBuilder().mergeFrom(data).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws com.google.protobuf.InvalidProtocolBufferException
		{
			return newBuilder().mergeFrom(data, extensionRegistry).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(java.io.InputStream input) throws java.io.IOException
		{
			return newBuilder().mergeFrom(input).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
		{
			return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException
		{
			Builder builder = newBuilder();
			if (builder.mergeDelimitedFrom(input))
			{
				return builder.buildParsed();
			}
			else
			{
				return null;
			}
		}

		public static netty.serialization.PersonFactory.Person parseDelimitedFrom(java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
		{
			Builder builder = newBuilder();
			if (builder.mergeDelimitedFrom(input, extensionRegistry))
			{
				return builder.buildParsed();
			}
			else
			{
				return null;
			}
		}

		public static netty.serialization.PersonFactory.Person parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException
		{
			return newBuilder().mergeFrom(input).buildParsed();
		}

		public static netty.serialization.PersonFactory.Person parseFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
		{
			return newBuilder().mergeFrom(input, extensionRegistry).buildParsed();
		}

		public static Builder newBuilder()
		{
			return Builder.create();
		}

		public Builder newBuilderForType()
		{
			return newBuilder();
		}

		public static Builder newBuilder(netty.serialization.PersonFactory.Person prototype)
		{
			return newBuilder().mergeFrom(prototype);
		}

		public Builder toBuilder()
		{
			return newBuilder(this);
		}

		@java.lang.Override
		protected Builder newBuilderForType(com.google.protobuf.GeneratedMessage.BuilderParent parent)
		{
			Builder builder = new Builder(parent);
			return builder;
		}

		public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements netty.serialization.PersonFactory.PersonOrBuilder
		{
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor()
			{
				return netty.serialization.PersonFactory.internal_static_Person_descriptor;
			}

			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable()
			{
				return netty.serialization.PersonFactory.internal_static_Person_fieldAccessorTable;
			}

			// Construct using netty.serialization.PersonFactory.Person.newBuilder()
			private Builder()
			{
				maybeForceBuilderInitialization();
			}

			private Builder(BuilderParent parent)
			{
				super(parent);
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization()
			{
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders)
				{
				}
			}

			private static Builder create()
			{
				return new Builder();
			}

			public Builder clear()
			{
				super.clear();
				age_ = 0;
				bitField0_ = (bitField0_ & ~0x00000001);
				name_ = "";
				bitField0_ = (bitField0_ & ~0x00000002);
				address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
				bitField0_ = (bitField0_ & ~0x00000004);
				sex_ = com.google.protobuf.Internal.stringDefaultValue("\304\320");
				bitField0_ = (bitField0_ & ~0x00000008);
				return this;
			}

			public Builder clone()
			{
				return create().mergeFrom(buildPartial());
			}

			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType()
			{
				return netty.serialization.PersonFactory.Person.getDescriptor();
			}

			public netty.serialization.PersonFactory.Person getDefaultInstanceForType()
			{
				return netty.serialization.PersonFactory.Person.getDefaultInstance();
			}

			public netty.serialization.PersonFactory.Person build()
			{
				netty.serialization.PersonFactory.Person result = buildPartial();
				if (!result.isInitialized())
				{
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			private netty.serialization.PersonFactory.Person buildParsed() throws com.google.protobuf.InvalidProtocolBufferException
			{
				netty.serialization.PersonFactory.Person result = buildPartial();
				if (!result.isInitialized())
				{
					throw newUninitializedMessageException(result).asInvalidProtocolBufferException();
				}
				return result;
			}

			public netty.serialization.PersonFactory.Person buildPartial()
			{
				netty.serialization.PersonFactory.Person result = new netty.serialization.PersonFactory.Person(this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001))
				{
					to_bitField0_ |= 0x00000001;
				}
				result.age_ = age_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002))
				{
					to_bitField0_ |= 0x00000002;
				}
				result.name_ = name_;
				if (((bitField0_ & 0x00000004) == 0x00000004))
				{
					address_ = new com.google.protobuf.UnmodifiableLazyStringList(address_);
					bitField0_ = (bitField0_ & ~0x00000004);
				}
				result.address_ = address_;
				if (((from_bitField0_ & 0x00000008) == 0x00000008))
				{
					to_bitField0_ |= 0x00000004;
				}
				result.sex_ = sex_;
				result.bitField0_ = to_bitField0_;
				onBuilt();
				return result;
			}

			public Builder mergeFrom(com.google.protobuf.Message other)
			{
				if (other instanceof netty.serialization.PersonFactory.Person)
				{
					return mergeFrom((netty.serialization.PersonFactory.Person) other);
				}
				else
				{
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(netty.serialization.PersonFactory.Person other)
			{
				if (other == netty.serialization.PersonFactory.Person.getDefaultInstance())
					return this;
				if (other.hasAge())
				{
					setAge(other.getAge());
				}
				if (other.hasName())
				{
					setName(other.getName());
				}
				if (!other.address_.isEmpty())
				{
					if (address_.isEmpty())
					{
						address_ = other.address_;
						bitField0_ = (bitField0_ & ~0x00000004);
					}
					else
					{
						ensureAddressIsMutable();
						address_.addAll(other.address_);
					}
					onChanged();
				}
				if (other.hasSex())
				{
					setSex(other.getSex());
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}

			public final boolean isInitialized()
			{
				if (!hasAge())
				{

					return false;
				}
				if (!hasName())
				{

					return false;
				}
				return true;
			}

			public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException
			{
				com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
				while (true)
				{
					int tag = input.readTag();
					switch (tag)
					{
						case 0:
							this.setUnknownFields(unknownFields.build());
							onChanged();
							return this;
						default:
						{
							if (!parseUnknownField(input, unknownFields, extensionRegistry, tag))
							{
								this.setUnknownFields(unknownFields.build());
								onChanged();
								return this;
							}
							break;
						}
						case 8:
						{
							bitField0_ |= 0x00000001;
							age_ = input.readInt32();
							break;
						}
						case 18:
						{
							bitField0_ |= 0x00000002;
							name_ = input.readBytes();
							break;
						}
						case 26:
						{
							ensureAddressIsMutable();
							address_.add(input.readBytes());
							break;
						}
						case 34:
						{
							bitField0_ |= 0x00000008;
							sex_ = input.readBytes();
							break;
						}
					}
				}
			}

			private int	bitField0_;

			// required int32 age = 1;
			private int	age_;

			public boolean hasAge()
			{
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}

			public int getAge()
			{
				return age_;
			}

			public Builder setAge(int value)
			{
				bitField0_ |= 0x00000001;
				age_ = value;
				onChanged();
				return this;
			}

			public Builder clearAge()
			{
				bitField0_ = (bitField0_ & ~0x00000001);
				age_ = 0;
				onChanged();
				return this;
			}

			// required string name = 2;
			private java.lang.Object name_ = "";

			public boolean hasName()
			{
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			public String getName()
			{
				java.lang.Object ref = name_;
				if (!(ref instanceof String))
				{
					String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
					name_ = s;
					return s;
				}
				else
				{
					return (String) ref;
				}
			}

			public Builder setName(String value)
			{
				if (value == null)
				{
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000002;
				name_ = value;
				onChanged();
				return this;
			}

			public Builder clearName()
			{
				bitField0_ = (bitField0_ & ~0x00000002);
				name_ = getDefaultInstance().getName();
				onChanged();
				return this;
			}

			void setName(com.google.protobuf.ByteString value)
			{
				bitField0_ |= 0x00000002;
				name_ = value;
				onChanged();
			}

			// repeated string address = 3;
			private com.google.protobuf.LazyStringList address_ = com.google.protobuf.LazyStringArrayList.EMPTY;

			private void ensureAddressIsMutable()
			{
				if (!((bitField0_ & 0x00000004) == 0x00000004))
				{
					address_ = new com.google.protobuf.LazyStringArrayList(address_);
					bitField0_ |= 0x00000004;
				}
			}

			public java.util.List<String> getAddressList()
			{
				return java.util.Collections.unmodifiableList(address_);
			}

			public int getAddressCount()
			{
				return address_.size();
			}

			public String getAddress(int index)
			{
				return address_.get(index);
			}

			public Builder setAddress(int index, String value)
			{
				if (value == null)
				{
					throw new NullPointerException();
				}
				ensureAddressIsMutable();
				address_.set(index, value);
				onChanged();
				return this;
			}

			public Builder addAddress(String value)
			{
				if (value == null)
				{
					throw new NullPointerException();
				}
				ensureAddressIsMutable();
				address_.add(value);
				onChanged();
				return this;
			}

			public Builder addAllAddress(java.lang.Iterable<String> values)
			{
				ensureAddressIsMutable();
				super.addAll(values, address_);
				onChanged();
				return this;
			}

			public Builder clearAddress()
			{
				address_ = com.google.protobuf.LazyStringArrayList.EMPTY;
				bitField0_ = (bitField0_ & ~0x00000004);
				onChanged();
				return this;
			}

			void addAddress(com.google.protobuf.ByteString value)
			{
				ensureAddressIsMutable();
				address_.add(value);
				onChanged();
			}

			// optional string sex = 4 [default = "\304\320"];
			private java.lang.Object sex_ = com.google.protobuf.Internal.stringDefaultValue("\304\320");

			public boolean hasSex()
			{
				return ((bitField0_ & 0x00000008) == 0x00000008);
			}

			public String getSex()
			{
				java.lang.Object ref = sex_;
				if (!(ref instanceof String))
				{
					String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
					sex_ = s;
					return s;
				}
				else
				{
					return (String) ref;
				}
			}

			public Builder setSex(String value)
			{
				if (value == null)
				{
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000008;
				sex_ = value;
				onChanged();
				return this;
			}

			public Builder clearSex()
			{
				bitField0_ = (bitField0_ & ~0x00000008);
				sex_ = getDefaultInstance().getSex();
				onChanged();
				return this;
			}

			void setSex(com.google.protobuf.ByteString value)
			{
				bitField0_ |= 0x00000008;
				sex_ = value;
				onChanged();
			}

			// @@protoc_insertion_point(builder_scope:Person)
		}

		static
		{
			defaultInstance = new Person(true);
			defaultInstance.initFields();
		}

		// @@protoc_insertion_point(class_scope:Person)
	}

	private static com.google.protobuf.Descriptors.Descriptor				internal_static_Person_descriptor;
	private static com.google.protobuf.GeneratedMessage.FieldAccessorTable	internal_static_Person_fieldAccessorTable;
	private static com.google.protobuf.Descriptors.Descriptor				internal_static_Person_Student_descriptor;
	private static com.google.protobuf.GeneratedMessage.FieldAccessorTable	internal_static_Person_Student_fieldAccessorTable;

	public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor()
	{
		return descriptor;
	}

	private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

	static
	{
		java.lang.String[] descriptorData =
		{ "\n\023PersonFactory.proto\"\225\001\n\006Person\022\013\n\003age\030" + "\001 \002(\005\022\014\n\004name\030\002 \002(\t\022\017\n\007address\030\003 \003(\t\022\017\n\003" + "sex\030\004 \001(\t:\002\304\320\032,\n\007Student\022\020\n\010stu_name\030\001 \002" + "(\t\022\017\n\007stu_age\030\002 \002(\005\" \n\006Phones\022\n\n\006PHONE1\020" + "\000\022\n\n\006PHONE2\020\001B\025\n\004netty.serializationB\rPersonFactory" };
		com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner()
		{
			public com.google.protobuf.ExtensionRegistry assignDescriptors(com.google.protobuf.Descriptors.FileDescriptor root)
			{
				descriptor = root;
				internal_static_Person_descriptor = getDescriptor().getMessageTypes().get(0);
				internal_static_Person_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_Person_descriptor, new java.lang.String[]
				{ "Age", "Name", "Address", "Sex", }, netty.serialization.PersonFactory.Person.class, netty.serialization.PersonFactory.Person.Builder.class);
				internal_static_Person_Student_descriptor = internal_static_Person_descriptor.getNestedTypes().get(0);
				internal_static_Person_Student_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(internal_static_Person_Student_descriptor, new java.lang.String[]
				{ "StuName", "StuAge", }, netty.serialization.PersonFactory.Person.Student.class, netty.serialization.PersonFactory.Person.Student.Builder.class);
				return null;
			}
		};
		com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[]
		{}, assigner);
	}

	// @@protoc_insertion_point(outer_class_scope)
}
